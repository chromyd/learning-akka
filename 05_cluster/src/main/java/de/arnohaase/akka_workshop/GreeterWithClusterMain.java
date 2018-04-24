package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import de.arnohaase.akka_workshop.greeter.Greeter;
import de.arnohaase.akka_workshop.httpserver.HttpServer;
import de.arnohaase.akka_workshop.vouchers.Vouchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeterWithClusterMain {
    private static final Logger log = LoggerFactory.getLogger(GreeterWithClusterMain.class);

    public static void main (String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create("greeter");
        final String httpHost = system.settings().config().getString("greeter.http-server.host");
        final int httpPort = system.settings().config().getInt("greeter.http-server.port");

        final ActorRef vouchers = createVouchers(system);
        final ActorRef greeter = system.actorOf(Greeter.props(vouchers), "greeter");
        system.actorOf(HttpServer.props (httpHost, httpPort, greeter, vouchers), "http-server");
    }

    private static ActorRef createVouchers(ActorSystem system) {
        ActorRef manager = system.actorOf(
                ClusterSingletonManager.props(
                        Vouchers.props(),
                        PoisonPill.getInstance(),
                        ClusterSingletonManagerSettings.create(system)),
                "vouchers-manager");

        return system.actorOf(
                ClusterSingletonProxy.props (
                        manager.path().toStringWithoutAddress(),
                        ClusterSingletonProxySettings.create(system)),
                "vouchers-proxy");
    }

}
