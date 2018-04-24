package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import de.arnohaase.akka_workshop.httpserver.HttpServer;
import de.arnohaase.akka_workshop.person.Persons;
import de.arnohaase.akka_workshop.vouchers.Vouchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardsMain {
    private static final Logger log = LoggerFactory.getLogger(ShardsMain.class);

    public static void main (String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create("greeter");
        final String httpHost = system.settings().config().getString("greeter.http-server.host");
        final int httpPort = system.settings().config().getInt("greeter.http-server.port");

        final ActorRef persons = system.actorOf(Persons.props());
        system.actorOf(HttpServer.props (httpHost, httpPort, persons), "http-server");
    }
}
