package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.PatternsCS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

public class HelloWorldMain {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldMain.class);

    public static void main (String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create();

        ActorRef greeter = system.actorOf(Greeter.props(), "greeter");
        greeter.tell (ImmutableGreetingRequest.of("Arno"), ActorRef.noSender());

        CompletionStage cs = PatternsCS.ask(greeter, ImmutableGreetingRequest.of("Ihr alle"), 1000);
        cs.whenComplete((msg,th) -> {
            log.info(msg.toString());
        });


        ActorRef translator = system.actorOf(Translator.props(), "translator");
        system.actorOf(HttpServer.props("localhost", 8080, greeter, translator), "http-server");

//        Thread.sleep(1000);
//        system.terminate();
    }
}
