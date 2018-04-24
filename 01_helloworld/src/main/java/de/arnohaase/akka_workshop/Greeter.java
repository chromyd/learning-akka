package de.arnohaase.akka_workshop;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import static de.arnohaase.akka_workshop.GreeterMessages.*;


public class Greeter extends AbstractActor {
    public static Props props() {
        return Props.create(Greeter.class);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(GreetingRequest.class, this::onGreetingRequest)
                .build();
    }

    private void onGreetingRequest(GreetingRequest msg) {
        log.info("yo " + msg.name());
        sender().tell(ImmutableGreetingResponse.of("Hello, " + msg.name() + "!"), self());
    }
}
