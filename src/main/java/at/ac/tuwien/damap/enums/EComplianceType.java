package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EComplianceType {

    INFORMED_CONSENT("by gaining informed consent for processing personal data"),
    ANONYMISATION("by anonymisation of personal data for preservation and/or sharing (truly anonymous data are no longer considered personal data)"),
    PSEUDONYMISATION("by pseudonymisation of personal data (the main differences with anonymisation ist that pseudonymisation is reversible)"),
    ENCRYPTION("by encryption of personal data (the encryption key must be stored separately from the data, for instance by a trusted third party)"),
    OTHER("other measures");

    private final String value;

    private static final HashMap<String, EComplianceType> MAP = new HashMap<>();

    EComplianceType(String value) {
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

    public static EComplianceType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EComplianceType option : EComplianceType.values()) {
            MAP.put(option.getValue(), option);
        }
    }

}
