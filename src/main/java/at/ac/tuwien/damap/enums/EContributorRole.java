package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EContributorRole {

    //TODO add contributor roles
    ;

    private final String role;

    EContributorRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String toString() {
        return role;
    }
}
