package at.ac.tuwien.damap.enums;

public enum EFunctionRole {

    ADMIN("ADMIN"),
    EDITOR("EDITOR"),
    GUEST("GUEST"),
    OWNER("OWNER"),
    SUPPORT("SUPPORT");

    private final String role;

    EFunctionRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
