package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EDataSource {
    NEW("new"),
    REUSED("reused");

    private final String value;

    private static final HashMap<String, EDataSource> MAP = new HashMap<>();

    EDataSource(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EDataSource getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EDataSource option : EDataSource.values()) {
            MAP.put(option.getValue(), option);
        }
    }
}
