package org.damap.base.enums;

import java.util.HashMap;

public enum EAgreement {
    CONSORTIUM_AGREEMENT("consortium agreement"),
    DATA_PROCESSING_AGREEMENT("data processing agreement"),
    JOINT_CONTROL_AGREEMENT("joint control agreement"),
    CONFIDENTIALITY_AGREEMENT("confidentiality agreement"),
    OTHER("other documents");

    private final String value;

    private static final HashMap<String, EAgreement> MAP = new HashMap<>();

    EAgreement(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return this.value;
    }

    public static EAgreement getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EAgreement option : EAgreement.values()) {
            MAP.put(option.getValue(), option);
        }
    }
}
