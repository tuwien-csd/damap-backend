package org.damap.base.enums;

import java.util.HashMap;

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

  @Override
  public String toString() {
    return value;
  }

  public String getValue() {
    return this.value;
  }

  public static ECostType getByValue(String value) {
    return MAP.get(value);
  }

  static {
    for (ECostType type : ECostType.values()) {
      MAP.put(type.getValue(), type);
    }
  }
}
