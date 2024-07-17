package org.damap.base.enums;

import java.util.HashMap;

/** EComplianceType class. */
public enum EComplianceType {
  INFORMED_CONSENT("by gaining informed consent for processing personal data"),
  ANONYMISATION("by anonymisation of personal data for preservation and/or sharing"),
  PSEUDONYMISATION("by pseudonymisation of personal data"),
  ENCRYPTION("by encryption of personal data"),
  OTHER("other measures");

  private final String value;

  private static final HashMap<String, EComplianceType> MAP = new HashMap<>();

  EComplianceType(String value) {
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
   * @return a {@link org.damap.base.enums.EComplianceType} object
   */
  public static EComplianceType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (EComplianceType option : EComplianceType.values()) {
      MAP.put(option.getValue(), option);
    }
  }
}
