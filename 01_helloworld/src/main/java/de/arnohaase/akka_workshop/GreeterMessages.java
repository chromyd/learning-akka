package de.arnohaase.akka_workshop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

public class GreeterMessages {
    @JsonSerialize
    @Value.Immutable 
    public interface GreetingRequest {
        @Value.Parameter String name();
    }

    @JsonSerialize
    @Value.Immutable
    public interface GreetingResponse {
        @Value.Parameter String greeting();
    }
}
