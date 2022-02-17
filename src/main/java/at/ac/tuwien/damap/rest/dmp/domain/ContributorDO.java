package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EContributorRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Damap compatible representation of persons
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorDO {

    private Long id;
    private String universityId;
    private IdentifierDO personId;
    private String firstName;
    private String lastName;
    private String mbox;
    private String affiliation;
    private IdentifierDO affiliationId;
    private boolean contact = false;
    private EContributorRole role;
    private String roleInProject;
}
