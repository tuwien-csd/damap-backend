package at.ac.tuwien.damap.enums;

public enum ESearchServiceType {

    UNIVERSITY,
    ORCID;

    String name;

    public static ESearchServiceType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
