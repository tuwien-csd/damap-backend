package at.ac.tuwien.damap.enums;

public enum EDataKind {

    UNKNOWN("UNKNOWN"),
    NONE("NONE"),
    SPECIFY("SPECIFY");

    private final String kind;

    EDataKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return kind;
    }
}
