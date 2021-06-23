package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum ECostType {

    dataAcquisition("Data Aquisition"),
    database("Database"),
    filebasedStorage("File-based Storage"),
    hardwareAndInfrastructure("Hardware and Infrastructure"),
    legalAdvice("Legal Advice"),
    personnel("Personnel"),
    repository("Repository"),
    sofwareLicense("Software License"),
    training("Training"),
    other("Other");

    private final String value;

    private static final HashMap<String, ECostType> MAP = new HashMap<String, ECostType>();

    ECostType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static ECostType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (ECostType type : ECostType.values()) {
            MAP.put(type.getValue(), type);
        }
    }
}
