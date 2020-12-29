package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.rest.projectdatabase.dto.ProjectMemberDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Damap compatible representation of project members
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDO {

    private String roleInProject;

    private PersonDO person;

}
