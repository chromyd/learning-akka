package de.arnohaase.akka_workshop.person;

import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.Status;
import akka.cluster.sharding.ShardRegion;
import akka.persistence.AbstractPersistentActor;
import scala.concurrent.duration.FiniteDuration;

import java.util.NoSuchElementException;


class PersonEntity extends AbstractPersistentActor {
    static Props props() {
        return Props.create(PersonEntity.class, PersonEntity::new);
    }

    @Override public String persistenceId () {
        return "person-" + self().path().name();
    }

    private PersonMessages.PersonUpdated person;

    private PersonEntity () {
        context().setReceiveTimeout(FiniteDuration.create(10, "s"));
    }

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(PersonMessages.GetPerson.class, this::onGet)
                .match(PersonMessages.UpdatePerson.class, this::onUpdate)
                .match(PersonMessages.CreatePerson.class, this::onCreate)
                .matchEquals(ReceiveTimeout.getInstance(), msg -> passivate())
                .build();
    }

    private void onCreate(PersonMessages.CreatePerson msg) {
        persist(ImmutablePersonUpdated.of(msg.name(), msg.address(), msg.entityId()), evt -> {
            person=evt;
            sender().tell(ImmutableGetPersonResponse.of(person.name(), person.address(), person.entityId()), self());
        });
    }

    private void onUpdate(PersonMessages.UpdatePerson msg) {
        if (person == null) {
            sender().tell(new Status.Failure(new NoSuchElementException()), self());
            passivate();
        }
        else {
            persist(ImmutablePersonUpdated.of(msg.name(), msg.address(), msg.entityId()), evt -> {
                person=evt;
                sender().tell(ImmutableGetPersonResponse.of(person.name(), person.address(), person.entityId()), self());
            });
        }
    }

    private void onGet(PersonMessages.GetPerson msg) {
        if (person == null) {
            sender().tell(new Status.Failure(new NoSuchElementException()), self());
            passivate();
        }
        else
            sender().tell(ImmutableGetPersonResponse.of(person.name(), person.address(), person.entityId()), self());
    }

    private void passivate() {
        context().parent().tell(new ShardRegion.Passivate (PoisonPill.getInstance()), self());
    }

    @Override public Receive createReceiveRecover () {
        return receiveBuilder()
                .match(PersonMessages.PersonUpdated.class, msg -> person=msg)
                .build();
    }
}
