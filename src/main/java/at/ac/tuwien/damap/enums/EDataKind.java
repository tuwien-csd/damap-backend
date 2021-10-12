package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EDataKind {

    UNKNOWN("UNKNOWN"),
    NONE("NONE"),
    SPECIFY("SPECIFY");

    private final String kind;

    EDataKind(String kind) {
        this.kind = kind;
    }

    @Override
    @JsonValue
    public String toString() {
        return kind;
    }
}
