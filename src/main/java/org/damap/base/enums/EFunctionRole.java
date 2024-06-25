package org.damap.base.enums;

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

  @Override
  public String toString() {
    return role;
  }
}
