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
}
