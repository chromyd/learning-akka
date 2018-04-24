package de.arnohaase.akka_workshop.greeter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

public class GreeterMessages {
    @JsonSerialize
    @Value.Immutable 
    public interface GreetingRequest {
        @Value.Parameter String name ();
    }

    @JsonSerialize
    @Value.Immutable
    public interface GreetingResponse {
        @Value.Parameter String greeting ();
    }

    @Value.Immutable
    interface Blacklist {
        @Value.Parameter List<String> blacklist ();
    }
}
