package de.arnohaase.akka_workshop.person;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;

import java.util.UUID;

public class Persons extends AbstractActor {
    public static Props props() {
        return Props.create(Persons.class, Persons::new);
    }

    final ShardRegion.MessageExtractor messageExtractor = new ShardRegion.HashCodeMessageExtractor(1000) {
        @Override public String entityId (Object message) {
            return ((PersonMessages.WithEntityId) message).entityId().toString();
        }
    };
    final ActorRef shardRegion = ClusterSharding.get(context().system()).start(
            "persons",
            PersonEntity.props(),
            ClusterShardingSettings.create(context().system()),
            messageExtractor
            );


    @Override
    public Receive createReceive () {
        return receiveBuilder()
                .match(PersonMessages.PersonData.class, this::onCreatePerson)
                .match(PersonMessages.WithEntityId.class, msg -> shardRegion.forward(msg, context()))
                .build();
    }

    private void onCreatePerson(PersonMessages.PersonData msg) {
        final UUID entityId = UUID.randomUUID();
        self().forward(ImmutableCreatePerson.of(msg.name(), msg.address(), entityId), context());
    }
}
