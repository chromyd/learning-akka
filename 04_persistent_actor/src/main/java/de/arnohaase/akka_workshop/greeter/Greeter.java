package de.arnohaase.akka_workshop.greeter;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.List;

import static de.arnohaase.akka_workshop.greeter.GreeterMessages.Blacklist;
import static de.arnohaase.akka_workshop.greeter.GreeterMessages.GreetingRequest;


public class Greeter extends AbstractActorWithStash {
    private static class ReadBlacklist{}
    public static Props props(ActorRef vouchers) {
        return Props.create(Greeter.class, () -> new Greeter(vouchers));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef vouchers;
    private List<String> blacklist = new ArrayList<>();

    private Greeter (ActorRef vouchers) {
        this.vouchers = vouchers;
        context().system().scheduler().schedule(Duration.Zero(), FiniteDuration.create(10, "s"), self(), new ReadBlacklist(), context().dispatcher(), ActorRef.noSender());
    }

    @Override public SupervisorStrategy supervisorStrategy () {
        return SupervisorStrategy.stoppingStrategy();
    }

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(ReadBlacklist.class, this::onReadBlacklist)
                .match(Blacklist.class, this::onBlacklist)
                .match(GreetingRequest.class, this::onGreetingRequest)
                .build();
    }

    private void onBlacklist(Blacklist msg) {
        log.info("read blacklist: " + msg);
        this.blacklist = msg.blacklist();
        unstashAll();
    }

    private void onReadBlacklist(ReadBlacklist msg) {
        context().actorOf(BlacklistFetcher.props(self()));
    }

    private void onGreetingRequest(GreetingRequest msg) {
        if (blacklist == null) {
            stash();
        }
        else {
            context().actorOf(GreeterWorker.props(msg.name(), blacklist, sender(), vouchers));
        }
    }
}
