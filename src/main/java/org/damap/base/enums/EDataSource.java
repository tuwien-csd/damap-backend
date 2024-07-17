package org.damap.base.enums;

import java.util.HashMap;

/** EDataSource class. */
public enum EDataSource {
  NEW("NEW"),
  REUSED("REUSED");

  private final String value;

  private static final HashMap<String, EDataSource> MAP = new HashMap<>();

  EDataSource(String value) {
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
   * @return a {@link org.damap.base.enums.EDataSource} object
   */
  public static EDataSource getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (EDataSource option : EDataSource.values()) {
      MAP.put(option.getValue(), option);
    }
  }
}
