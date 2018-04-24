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
 * Immutable implementation of {@link GreeterMessages.GreetingRequest}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableGreetingRequest.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableGreetingRequest.of()}.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "GreeterMessages.GreetingRequest"})
public final class ImmutableGreetingRequest
    implements GreeterMessages.GreetingRequest {
  private final String name;

  private ImmutableGreetingRequest(String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }

  private ImmutableGreetingRequest(ImmutableGreetingRequest original, String name) {
    this.name = name;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @JsonProperty("name")
  @Override
  public String name() {
    return name;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GreeterMessages.GreetingRequest#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGreetingRequest withName(String value) {
    if (this.name.equals(value)) return this;
    String newValue = Preconditions.checkNotNull(value, "name");
    return new ImmutableGreetingRequest(this, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableGreetingRequest} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableGreetingRequest
        && equalTo((ImmutableGreetingRequest) another);
  }

  private boolean equalTo(ImmutableGreetingRequest another) {
    return name.equals(another.name);
  }

  /**
   * Computes a hash code from attributes: {@code name}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + name.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code GreetingRequest} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("GreetingRequest")
        .omitNullValues()
        .add("name", name)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements GreeterMessages.GreetingRequest {
    String name;
    @JsonProperty("name")
    public void setName(String name) {
      this.name = name;
    }
    @Override
    public String name() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ImmutableGreetingRequest fromJson(Json json) {
    ImmutableGreetingRequest.Builder builder = ImmutableGreetingRequest.builder();
    if (json.name != null) {
      builder.name(json.name);
    }
    return builder.build();
  }

  /**
   * Construct a new immutable {@code GreetingRequest} instance.
   * @param name The value for the {@code name} attribute
   * @return An immutable GreetingRequest instance
   */
  public static ImmutableGreetingRequest of(String name) {
    return new ImmutableGreetingRequest(name);
  }

  /**
   * Creates an immutable copy of a {@link GreeterMessages.GreetingRequest} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable GreetingRequest instance
   */
  public static ImmutableGreetingRequest copyOf(GreeterMessages.GreetingRequest instance) {
    if (instance instanceof ImmutableGreetingRequest) {
      return (ImmutableGreetingRequest) instance;
    }
    return ImmutableGreetingRequest.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableGreetingRequest ImmutableGreetingRequest}.
   * @return A new ImmutableGreetingRequest builder
   */
  public static ImmutableGreetingRequest.Builder builder() {
    return new ImmutableGreetingRequest.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableGreetingRequest ImmutableGreetingRequest}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_NAME = 0x1L;
    private long initBits = 0x1L;

    private String name;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code GreetingRequest} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(GreeterMessages.GreetingRequest instance) {
      Preconditions.checkNotNull(instance, "instance");
      name(instance.name());
      return this;
    }

    /**
     * Initializes the value for the {@link GreeterMessages.GreetingRequest#name() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder name(String name) {
      this.name = Preconditions.checkNotNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Builds a new {@link ImmutableGreetingRequest ImmutableGreetingRequest}.
     * @return An immutable instance of GreetingRequest
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableGreetingRequest build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableGreetingRequest(null, name);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      return "Cannot build GreetingRequest, some of required attributes are not set " + attributes;
    }
  }
}
