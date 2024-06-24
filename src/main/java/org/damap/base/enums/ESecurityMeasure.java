package org.damap.base.enums;

import java.util.HashMap;

public enum ESecurityMeasure {
    INDIVIDUAL_LOGIN("individual log-in and password"),
    ADDITIONAL_LOGIN("additional log-in for specific applications"),
    AUTOMATIC_LOCKING_OF_CLIENTS("automatic locking of clients (timeout)"),
    ENCRYPTION_OF_SYSTEMS("encryption of systems"),
    LOCKED_STORAGE("keep printouts of sensitive data in locked storage"),
    OTHER("other measures");

    private final String value;

    private static final HashMap<String, ESecurityMeasure> MAP = new HashMap<>();

    ESecurityMeasure(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static ESecurityMeasure getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (ESecurityMeasure option : ESecurityMeasure.values()) {
            MAP.put(option.getValue(), option);
        }
    }
}
