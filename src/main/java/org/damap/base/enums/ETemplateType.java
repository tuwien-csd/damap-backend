package org.damap.base.enums;

import java.util.HashMap;

/** ETemplateType class. */
public enum ETemplateType {
  SCIENCE_EUROPE("SCIENCE_EUROPE"),
  FWF("FWF"),
  HORIZON_EUROPE("HORIZON_EUROPE");

  private final String value;

  private static final HashMap<String, ETemplateType> MAP = new HashMap<>();

  ETemplateType(String value) {
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
   * @return a {@link org.damap.base.enums.ETemplateType} object
   */
  public static ETemplateType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (ETemplateType type : ETemplateType.values()) {
      MAP.put(type.getValue(), type);
    }
  }
}
