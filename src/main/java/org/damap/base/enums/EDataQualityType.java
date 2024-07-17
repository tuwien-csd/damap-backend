package org.damap.base.enums;

import java.util.HashMap;

/** EDataQualityType class. */
public enum EDataQualityType {
  CALIBRATION("calibration"),
  REPEATED_SAMPLES_OR_MEASUREMENTS("repeated samples or measurements"),
  STANDARDISED_DATA_CAPTURE("standardised data capture"),
  DATA_ENTRY_VALIDATION("data entry validation"),
  PEER_REVIEW_OF_DATA("peer review of data"),
  REPRESENTATION_WITH_CONTROLLED_VOCABULARIES("representation with controlled vocabularies"),
  OTHERS("others");

  private final String value;

  private static final HashMap<String, EDataQualityType> MAP = new HashMap<>();

  EDataQualityType(String value) {
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
   * @return a {@link org.damap.base.enums.EDataQualityType} object
   */
  public static EDataQualityType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (EDataQualityType option : EDataQualityType.values()) {
      MAP.put(option.getValue(), option);
    }
  }
}
