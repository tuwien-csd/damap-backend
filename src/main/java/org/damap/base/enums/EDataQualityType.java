package org.damap.base.enums;

import java.util.HashMap;

public enum EDataQualityType {

    CALIBRATION("calibration"),
    REPEATED_SAMPLES_OR_MEASUREMENTS("repeated samples or measurements"),
    STANDARDISED_DATA_CAPTURE("standardised data capture"),
    DATA_ENTRY_VALIDATION("data entry validation"),
    PEER_REVIEW_OF_DATA("peer review of data"),
    REPRESENTATION_WITH_CONTROLLED_VOCABULARIES("representation with controlled vocabularies"),
    OTHERS("others");

    private final String value;

    private static final HashMap<String, EDataQualityType> MAP = new HashMap<>();

    EDataQualityType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EDataQualityType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EDataQualityType option : EDataQualityType.values()) {
            MAP.put(option.getValue(), option);
        }
    }
}
