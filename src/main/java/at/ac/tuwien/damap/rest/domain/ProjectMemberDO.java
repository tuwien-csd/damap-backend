package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.rest.projectdatabase.dto.ProjectMemberDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Damap compatible representation of project members
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDO {

    private String roleInProject;

    private PersonDO person;

}
