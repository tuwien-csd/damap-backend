package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Damap compatible representation of project members
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDO {

    private String roleInProject;
    private boolean projectLeader;

    private PersonDO person;

}
