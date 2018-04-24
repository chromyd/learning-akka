package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.arnohaase.akka_workshop.greeter.Greeter;
import de.arnohaase.akka_workshop.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeterWithTimeoutMain {
    private static final Logger log = LoggerFactory.getLogger(GreeterWithTimeoutMain.class);

    public static void main (String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create();

        ActorRef greeter = system.actorOf(Greeter.props(), "greeter");
        system.actorOf(HttpServer.props("localhost", 8080, greeter), "http-server");
    }
}
