package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum EContributorRole {

    DataCollector("Data Collector"),
    DataCurator("Data Curator"),
    DataManager("Data Manager"),
    Distributor("Distributor"),
    Editor("Editor"),
    HostingInstitution("Hosting Institution"),
    Producer("Producer"),
    ProjectLeader("Project Leader"),
    ProjectManager("Project Manager"),
    ProjectMember("Project Member"),
    RegistrationAgency("Registration Agency"),
    RegistrationAuthority("Registration Authority"),
    RelatedPerson("Related Person"),
    Researcher("Researcher"),
    ResearchGroup("Research Group"),
    RightsHolder("Rights Holder"),
    Sponsor("Sponsor"),
    Supervisor("Supervisor"),
    WorkPackageLeader("Work Package Leader"),
    Other("Other");

    private final String role;

    private static final HashMap<String, EContributorRole> MAP = new HashMap<String, EContributorRole>();

    EContributorRole(String role) {
        this.role = role;
    }

    @JsonValue
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
