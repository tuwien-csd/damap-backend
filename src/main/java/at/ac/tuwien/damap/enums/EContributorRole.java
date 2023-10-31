package at.ac.tuwien.damap.enums;

import java.util.HashMap;

public enum EContributorRole {

    DATA_COLLECTOR("Data Collector"),
    DATA_CURATOR("Data Curator"),
    DATA_MANAGER("Data Manager"),
    DISTRIBUTOR("Distributor"),
    EDITOR("Editor"),
    HOSTING_INSTITUTION("Hosting Institution"),
    PRODUCER("Producer"),
    PROJECT_LEADER("Project Leader"),
    PROJECT_MANAGER("Project Manager"),
    PROJECT_MEMBER("Project Member"),
    REGISTRATION_AGENCY("Registration Agency"),
    REGISTRATION_AUTHORITY("Registration Authority"),
    RELATED_PERSON("Related Person"),
    RESEARCHER("Researcher"),
    RESEARCH_GROUP("Research Group"),
    RIGHTS_HOLDER("Rights Holder"),
    SPONSOR("Sponsor"),
    SUPERVISOR("Supervisor"),
    WORK_PACKAGE_LEADER("Work Package Leader"),
    OTHER("Other");

    private final String role;

    private static final HashMap<String, EContributorRole> MAP = new HashMap<>();

    EContributorRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

    public String getRole() {
        return this.role;
    }

    public static EContributorRole getByRole(String role) {
        return MAP.get(role);
    }

    static {
        for (EContributorRole role : EContributorRole.values()) {
            MAP.put(role.getRole(), role);
        }
    }

}
