package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EDataType {

    STANDARD_OFFICE_DOCUMENTS("Standard office documents"),
    NETWORKBASED_DATA("Networkbased data"),
    DATABASES("Databases"),
    IMAGES("Images"),
    STRUCTURED_GRAPHICS("Structured graphics"),
    AUDIOVISUAL_DATA("Audiovisual data"),
    SCIENTIFIC_STATISTICAL_DATA("Scientific and statistical data formats"),
    RAW_DATA("Raw data"),
    PLAIN_TEXT("Plain text"),
    STRUCTURED_TEXT("Structured text"),
    ARCHIVED_DATA("Archived data"),
    SOFTWARE_APPLICATIONS("Software applications"),
    SOURCE_CODE("Source code"),
    CONFIGURATION_DATA("Configuration data"),
    OTHER("Other");

    private final String value;

    private static final HashMap<String, EDataType> MAP = new HashMap<>();

    EDataType(String value) {
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

    public static EDataType getByValue(String value) {
        return MAP.get(value);
    }

    static {
        for (EDataType type : EDataType.values()) {
            MAP.put(type.getValue(), type);
        }
    }
}
