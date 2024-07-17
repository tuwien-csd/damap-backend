package org.damap.base.enums;

import java.util.HashMap;

/** EDataType class. */
public enum EDataType {
  STANDARD_OFFICE_DOCUMENTS("Standard office documents"),
  NETWORKBASED_DATA("Networkbased data"),
  DATABASES("Databases"),
  IMAGES("Images"),
  STRUCTURED_GRAPHICS("Structured graphics"),
  AUDIOVISUAL_DATA("Audiovisual data"),
  SCIENTIFIC_STATISTICAL_DATA("Scientific and statistical data formats"),
  RAW_DATA("Raw data"),
  PLAIN_TEXT("Plain text"),
  STRUCTURED_TEXT("Structured text"),
  ARCHIVED_DATA("Archived data"),
  SOFTWARE_APPLICATIONS("Software applications"),
  SOURCE_CODE("Source code"),
  CONFIGURATION_DATA("Configuration data"),
  OTHER("Other");

  private final String value;

  private static final HashMap<String, EDataType> MAP = new HashMap<>();

  EDataType(String value) {
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
   * @return a {@link org.damap.base.enums.EDataType} object
   */
  public static EDataType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (EDataType type : EDataType.values()) {
      MAP.put(type.getValue(), type);
    }
  }
}
