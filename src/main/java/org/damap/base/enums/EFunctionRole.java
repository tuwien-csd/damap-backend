package org.damap.base.enums;

/** EFunctionRole class. */
public enum EFunctionRole {
  ADMIN("ADMIN"),
  EDITOR("EDITOR"),
  GUEST("GUEST"),
  OWNER("OWNER"),
  SUPPORT("SUPPORT");

  private final String role;

  EFunctionRole(String role) {
    this.role = role;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return role;
  }
}
