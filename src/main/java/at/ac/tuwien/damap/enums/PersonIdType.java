package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PersonIdType {
    ORCID("ORCID"),
    ISNI("ISNI"),
    OPENID("OPENID");

    PersonIdType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String toString() {
        return type;
    }
}
