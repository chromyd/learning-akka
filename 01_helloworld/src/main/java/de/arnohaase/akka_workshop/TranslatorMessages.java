package de.arnohaase.akka_workshop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

public class TranslatorMessages {
    @JsonSerialize
    @Value.Immutable
    public interface TranslationRequest {
        @Value.Parameter String text();
    }

    @JsonSerialize
    @Value.Immutable
    public interface TranslationResponse {
        @Value.Parameter String text();
    }
}
