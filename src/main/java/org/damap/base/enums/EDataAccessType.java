package org.damap.base.enums;

import java.util.HashMap;

/** EDataAccessType class. */
public enum EDataAccessType {
  OPEN("Open"),
  RESTRICTED("Restricted"),
  CLOSED("Closed");

  private final String value;

  private static final HashMap<String, EDataAccessType> MAP = new HashMap<>();

  EDataAccessType(String value) {
    this.value = value;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return value;
  }

  /**
   * Getter for the field <code>value</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getValue() {
    return this.value;
  }

  /**
   * getByValue.
   *
   * @param value a {@link java.lang.String} object
   * @return a {@link org.damap.base.enums.EDataAccessType} object
   */
  public static EDataAccessType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (EDataAccessType type : EDataAccessType.values()) {
      MAP.put(type.getValue(), type);
    }
  }

  /**
   * Compares this {@code EDataAccessType} instance with another {@code EDataAccessType} to
   * determine which is more restrictive. The order of restriction is defined as:
   *
   * <ul>
   *   <li>{@code CLOSED} is the most restrictive.
   *   <li>{@code RESTRICTED} is more restrictive than {@code OPEN} but less restrictive than {@code
   *       CLOSED}.
   *   <li>{@code OPEN} is the least restrictive.
   * </ul>
   *
   * @param other the other {@code EDataAccessType} to compare to; can be {@code null}. If {@code
   *     null}, this instance is considered more restrictive.
   * @return {@code 1} if this instance is more restrictive than {@code other}, {@code -1} if this
   *     instance is less restrictive than {@code other}, and {@code 0} if both instances are equal.
   */
  public int compare(EDataAccessType other) {
    if (other == null) {
      return 1;
    }

    if (this == other) {
      return 0;
    }

    return switch (this) {
      case CLOSED -> 1;
      case RESTRICTED -> (other == CLOSED) ? -1 : 1;
      case OPEN -> -1;
    };
  }
}
