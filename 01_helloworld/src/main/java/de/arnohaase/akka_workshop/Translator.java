package de.arnohaase.akka_workshop;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static de.arnohaase.akka_workshop.TranslatorMessages.TranslationRequest;


public class Translator extends AbstractActor {
    public static Props props() {
        return Props.create(Translator.class);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(TranslationRequest.class, this::onGreetingRequest)
                .build();
    }

    private void onGreetingRequest(TranslationRequest msg) {
        log.info("x-late: " + msg.text());
        sender().tell(ImmutableTranslationResponse.of(msg.text().toUpperCase()), self());
    }
}
