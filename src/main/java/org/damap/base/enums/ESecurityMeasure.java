package org.damap.base.enums;

import java.util.HashMap;

/** ESecurityMeasure class. */
public enum ESecurityMeasure {
  INDIVIDUAL_LOGIN("individual log-in and password"),
  ADDITIONAL_LOGIN("additional log-in for specific applications"),
  AUTOMATIC_LOCKING_OF_CLIENTS("automatic locking of clients (timeout)"),
  ENCRYPTION_OF_SYSTEMS("encryption of systems"),
  LOCKED_STORAGE("keep printouts of sensitive data in locked storage"),
  OTHER("other measures");

  private final String value;

  private static final HashMap<String, ESecurityMeasure> MAP = new HashMap<>();

  ESecurityMeasure(String value) {
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
   * @return a {@link org.damap.base.enums.ESecurityMeasure} object
   */
  public static ESecurityMeasure getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (ESecurityMeasure option : ESecurityMeasure.values()) {
      MAP.put(option.getValue(), option);
    }
  }
}
