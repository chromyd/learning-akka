package de.arnohaase.akka_workshop.httpserver;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.CoordinatedShutdown;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import de.arnohaase.akka_workshop.greeter.ImmutableGreetingRequest;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.segment;

public class HttpServer extends AbstractActor {
    public static Props props(String host, int port, ActorRef greetings) {
        return Props.create(HttpServer.class, () -> new HttpServer(host, port, greetings));
    }

    public HttpServer (String host, int port, ActorRef greetings) {
        final Http http = Http.get(context().system());
        final Materializer mat = ActorMaterializer.create(context().system());

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = new Routes().createRoute(greetings).flow(context().system(), mat);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow, ConnectHttp.toHost(host, port), mat);

        CoordinatedShutdown.get(context().system())
                .addTask(CoordinatedShutdown.PhaseServiceUnbind(),
                        "http-server",
                        () -> binding
                                .thenCompose(ServerBinding::unbind)
                                .thenApply(x -> Done.getInstance())
                );
    }


    @Override public Receive createReceive () {
        return AbstractActor.emptyBehavior();
    }

    private class Routes extends AllDirectives {
        private Route createRoute(ActorRef greetings) {
            return handleExceptions(
                    ExceptionHandler.newBuilder()
                            .match(NoSuchElementException.class, exc -> complete(StatusCodes.NOT_FOUND))
                            .build(),
                    () -> route (
                            path(segment("greetings").slash(segment()),
                                    name -> get(() ->
                                            completeOKWithFuture (PatternsCS.ask(greetings, ImmutableGreetingRequest.of(name), 1000), Jackson.marshaller())
                                    )
                            )
                    ));
        }
    }
}
