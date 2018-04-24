package de.arnohaase.akka_workshop.person;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;
import java.util.UUID;


public class PersonMessages {
    public interface WithEntityId extends Serializable {
        @Value.Parameter UUID entityId();
    }

    @JsonSerialize
    @Value.Immutable
    public interface CreatePerson extends WithEntityId {
        @Value.Parameter String name();
        @Value.Parameter String address();
    }

    @JsonSerialize
    @Value.Immutable
    public interface UpdatePerson extends WithEntityId {
        @Value.Parameter String name();
        @Value.Parameter String address();
    }

    @JsonSerialize
    @Value.Immutable
    public interface GetPerson extends WithEntityId {}

    @JsonSerialize
    @Value.Immutable
    public interface GetPersonResponse extends WithEntityId {
        @Value.Parameter String name();
        @Value.Parameter String address();
    }

    @JsonSerialize
    @Value.Immutable
    interface PersonUpdated extends WithEntityId {
        @Value.Parameter String name();
        @Value.Parameter String address();
    }

    @JsonSerialize
    @Value.Immutable
    public interface PersonData {
        @Value.Parameter String name();
        @Value.Parameter String address();
    }
}
