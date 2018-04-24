package de.arnohaase.akka_workshop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link GreeterMessages.GreetingResponse}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableGreetingResponse.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableGreetingResponse.of()}.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "GreeterMessages.GreetingResponse"})
public final class ImmutableGreetingResponse
    implements GreeterMessages.GreetingResponse {
  private final String greeting;

  private ImmutableGreetingResponse(String greeting) {
    this.greeting = Preconditions.checkNotNull(greeting, "greeting");
  }

  private ImmutableGreetingResponse(ImmutableGreetingResponse original, String greeting) {
    this.greeting = greeting;
  }

  /**
   * @return The value of the {@code greeting} attribute
   */
  @JsonProperty("greeting")
  @Override
  public String greeting() {
    return greeting;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GreeterMessages.GreetingResponse#greeting() greeting} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for greeting
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGreetingResponse withGreeting(String value) {
    if (this.greeting.equals(value)) return this;
    String newValue = Preconditions.checkNotNull(value, "greeting");
    return new ImmutableGreetingResponse(this, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableGreetingResponse} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableGreetingResponse
        && equalTo((ImmutableGreetingResponse) another);
  }

  private boolean equalTo(ImmutableGreetingResponse another) {
    return greeting.equals(another.greeting);
  }

  /**
   * Computes a hash code from attributes: {@code greeting}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + greeting.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code GreetingResponse} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("GreetingResponse")
        .omitNullValues()
        .add("greeting", greeting)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements GreeterMessages.GreetingResponse {
    String greeting;
    @JsonProperty("greeting")
    public void setGreeting(String greeting) {
      this.greeting = greeting;
    }
    @Override
    public String greeting() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ImmutableGreetingResponse fromJson(Json json) {
    ImmutableGreetingResponse.Builder builder = ImmutableGreetingResponse.builder();
    if (json.greeting != null) {
      builder.greeting(json.greeting);
    }
    return builder.build();
  }

  /**
   * Construct a new immutable {@code GreetingResponse} instance.
   * @param greeting The value for the {@code greeting} attribute
   * @return An immutable GreetingResponse instance
   */
  public static ImmutableGreetingResponse of(String greeting) {
    return new ImmutableGreetingResponse(greeting);
  }

  /**
   * Creates an immutable copy of a {@link GreeterMessages.GreetingResponse} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable GreetingResponse instance
   */
  public static ImmutableGreetingResponse copyOf(GreeterMessages.GreetingResponse instance) {
    if (instance instanceof ImmutableGreetingResponse) {
      return (ImmutableGreetingResponse) instance;
    }
    return ImmutableGreetingResponse.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableGreetingResponse ImmutableGreetingResponse}.
   * @return A new ImmutableGreetingResponse builder
   */
  public static ImmutableGreetingResponse.Builder builder() {
    return new ImmutableGreetingResponse.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableGreetingResponse ImmutableGreetingResponse}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_GREETING = 0x1L;
    private long initBits = 0x1L;

    private String greeting;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code GreetingResponse} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(GreeterMessages.GreetingResponse instance) {
      Preconditions.checkNotNull(instance, "instance");
      greeting(instance.greeting());
      return this;
    }

    /**
     * Initializes the value for the {@link GreeterMessages.GreetingResponse#greeting() greeting} attribute.
     * @param greeting The value for greeting 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder greeting(String greeting) {
      this.greeting = Preconditions.checkNotNull(greeting, "greeting");
      initBits &= ~INIT_BIT_GREETING;
      return this;
    }

    /**
     * Builds a new {@link ImmutableGreetingResponse ImmutableGreetingResponse}.
     * @return An immutable instance of GreetingResponse
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableGreetingResponse build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableGreetingResponse(null, greeting);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_GREETING) != 0) attributes.add("greeting");
      return "Cannot build GreetingResponse, some of required attributes are not set " + attributes;
    }
  }
}
