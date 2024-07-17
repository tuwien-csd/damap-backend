package org.damap.base.enums;

import java.util.HashMap;

/** ECostType class. */
public enum ECostType {
  DATA_ACQUISITION("Data Aquisition"),
  DATABASE("Database"),
  FILE_BASED_STORAGE("File-based Storage"),
  HARDWARE_AND_INFRASTRUCTURE("Hardware and Infrastructure"),
  LEGAL_ADVICE("Legal Advice"),
  PERSONNEL("Personnel"),
  REPOSITORY("Repository"),
  SOFTWARE_LICENSE("Software License"),
  TRAINING("Training"),
  OTHER("Other");

  private final String value;

  private static final HashMap<String, ECostType> MAP = new HashMap<>();

  ECostType(String value) {
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
   * @return a {@link org.damap.base.enums.ECostType} object
   */
  public static ECostType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (ECostType type : ECostType.values()) {
      MAP.put(type.getValue(), type);
    }
  }
}
