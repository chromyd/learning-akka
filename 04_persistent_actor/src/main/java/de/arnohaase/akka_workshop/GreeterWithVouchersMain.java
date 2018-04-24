package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.arnohaase.akka_workshop.greeter.Greeter;
import de.arnohaase.akka_workshop.httpserver.HttpServer;
import de.arnohaase.akka_workshop.vouchers.Vouchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeterWithVouchersMain {
    private static final Logger log = LoggerFactory.getLogger(GreeterWithVouchersMain.class);

    public static void main (String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create();

        final ActorRef vouchers = system.actorOf(Vouchers.props(), "vouchers");
        final ActorRef greeter = system.actorOf(Greeter.props(vouchers), "greeter");
        system.actorOf(HttpServer.props("localhost", 8080, greeter, vouchers), "http-server");
    }
}
