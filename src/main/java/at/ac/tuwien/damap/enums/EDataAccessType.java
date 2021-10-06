package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EDataAccessType {

    OPEN("Open"),
    RESTRICTED("Restricted"),
    CLOSED("Closed");

    private final String value;

    private static final HashMap<String, EDataAccessType> MAP = new HashMap<String, EDataAccessType>();

    EDataAccessType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EDataAccessType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EDataAccessType type : EDataAccessType.values()) {
            MAP.put(type.getValue(), type);
        }
    }
}
