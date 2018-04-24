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
 * Immutable implementation of {@link TranslatorMessages.TranslationRequest}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTranslationRequest.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableTranslationRequest.of()}.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "TranslatorMessages.TranslationRequest"})
public final class ImmutableTranslationRequest
    implements TranslatorMessages.TranslationRequest {
  private final String text;

  private ImmutableTranslationRequest(String text) {
    this.text = Preconditions.checkNotNull(text, "text");
  }

  private ImmutableTranslationRequest(ImmutableTranslationRequest original, String text) {
    this.text = text;
  }

  /**
   * @return The value of the {@code text} attribute
   */
  @JsonProperty("text")
  @Override
  public String text() {
    return text;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TranslatorMessages.TranslationRequest#text() text} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for text
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTranslationRequest withText(String value) {
    if (this.text.equals(value)) return this;
    String newValue = Preconditions.checkNotNull(value, "text");
    return new ImmutableTranslationRequest(this, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTranslationRequest} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTranslationRequest
        && equalTo((ImmutableTranslationRequest) another);
  }

  private boolean equalTo(ImmutableTranslationRequest another) {
    return text.equals(another.text);
  }

  /**
   * Computes a hash code from attributes: {@code text}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + text.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TranslationRequest} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("TranslationRequest")
        .omitNullValues()
        .add("text", text)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements TranslatorMessages.TranslationRequest {
    String text;
    @JsonProperty("text")
    public void setText(String text) {
      this.text = text;
    }
    @Override
    public String text() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ImmutableTranslationRequest fromJson(Json json) {
    ImmutableTranslationRequest.Builder builder = ImmutableTranslationRequest.builder();
    if (json.text != null) {
      builder.text(json.text);
    }
    return builder.build();
  }

  /**
   * Construct a new immutable {@code TranslationRequest} instance.
   * @param text The value for the {@code text} attribute
   * @return An immutable TranslationRequest instance
   */
  public static ImmutableTranslationRequest of(String text) {
    return new ImmutableTranslationRequest(text);
  }

  /**
   * Creates an immutable copy of a {@link TranslatorMessages.TranslationRequest} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TranslationRequest instance
   */
  public static ImmutableTranslationRequest copyOf(TranslatorMessages.TranslationRequest instance) {
    if (instance instanceof ImmutableTranslationRequest) {
      return (ImmutableTranslationRequest) instance;
    }
    return ImmutableTranslationRequest.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTranslationRequest ImmutableTranslationRequest}.
   * @return A new ImmutableTranslationRequest builder
   */
  public static ImmutableTranslationRequest.Builder builder() {
    return new ImmutableTranslationRequest.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTranslationRequest ImmutableTranslationRequest}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_TEXT = 0x1L;
    private long initBits = 0x1L;

    private String text;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TranslationRequest} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TranslatorMessages.TranslationRequest instance) {
      Preconditions.checkNotNull(instance, "instance");
      text(instance.text());
      return this;
    }

    /**
     * Initializes the value for the {@link TranslatorMessages.TranslationRequest#text() text} attribute.
     * @param text The value for text 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder text(String text) {
      this.text = Preconditions.checkNotNull(text, "text");
      initBits &= ~INIT_BIT_TEXT;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTranslationRequest ImmutableTranslationRequest}.
     * @return An immutable instance of TranslationRequest
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTranslationRequest build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTranslationRequest(null, text);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_TEXT) != 0) attributes.add("text");
      return "Cannot build TranslationRequest, some of required attributes are not set " + attributes;
    }
  }
}
