package org.damap.base.enums;

import java.util.HashMap;

public enum EAccessRight {

    READ("reading only"),
    WRITE("writing"),
    NONE("no access");

    private final String value;

    private static final HashMap<String, EAccessRight> MAP = new HashMap<>();

    EAccessRight(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EAccessRight getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EAccessRight option : EAccessRight.values()) {
            MAP.put(option.getValue(), option);
        }
    }
}
