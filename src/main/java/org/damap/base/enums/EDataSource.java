package org.damap.base.enums;

import java.util.HashMap;

public enum EDataSource {
    NEW("NEW"),
    REUSED("REUSED");

    private final String value;

    private static final HashMap<String, EDataSource> MAP = new HashMap<>();

    EDataSource(String value) {
        this.value = value;
    }

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
