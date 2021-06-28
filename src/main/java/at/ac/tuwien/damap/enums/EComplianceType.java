package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EComplianceType {

    informedConsent("by gaining informed consent"),
    encryption("by encryption"),
    anonymisation("by anonymisation"),
    pseudonymisation("by pseudonymisation"),
    other("other");

    private final String value;

    private static final HashMap<String, EComplianceType> MAP = new HashMap<>();

    EComplianceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EComplianceType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EComplianceType option : EComplianceType.values()) {
            MAP.put(option.getValue(), option);
        }
    }

}
