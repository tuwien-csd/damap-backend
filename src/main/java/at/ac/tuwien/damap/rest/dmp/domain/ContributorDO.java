package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EContributorRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Damap compatible representation of persons
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorDO {

    private Long id;
    @Size(max = 255)
    private String universityId;
    private IdentifierDO personId;
    @Size(max = 255)
    private String firstName;
    @Size(max = 255)
    private String lastName;
    @Size(max = 255)
    private String mbox;
    @Size(max = 255)
    private String affiliation;
    private IdentifierDO affiliationId;
    private boolean contact = false;
    private EContributorRole role;
    //not stored in DB, therefore no size contraint
    private String roleInProject;
}
