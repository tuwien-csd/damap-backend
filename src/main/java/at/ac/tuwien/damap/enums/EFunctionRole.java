package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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
    @JsonValue
    public String toString() {
        return role;
    }
}
