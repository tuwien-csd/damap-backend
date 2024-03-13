package at.ac.tuwien.damap.enums;

import java.util.HashMap;

public enum ETemplateType {

    SCIENCE_EUROPE("SCIENCE_EUROPE"),
    FWF("FWF"),
    HORIZON_EUROPE("HORIZON_EUROPE");

    private final String value;

    private static final HashMap<String, ETemplateType> MAP = new HashMap<>();

    ETemplateType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static ETemplateType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (ETemplateType type : ETemplateType.values()) {
            MAP.put(type.getValue(), type);
        }
    }
}
