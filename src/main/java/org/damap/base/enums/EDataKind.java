package org.damap.base.enums;

/** EDataKind class. */
public enum EDataKind {
  UNKNOWN("UNKNOWN"),
  NONE("NONE"),
  SPECIFY("SPECIFY");

  private final String kind;

  EDataKind(String kind) {
    this.kind = kind;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return kind;
  }
}
