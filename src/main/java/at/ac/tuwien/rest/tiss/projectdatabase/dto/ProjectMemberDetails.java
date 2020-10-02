package at.ac.tuwien.rest.tiss.projectdatabase.dto;

import at.ac.tuwien.rest.tiss.addressbook.dto.PersonDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDetails {

    @Getter
    @Setter
    private String roleInProject;

    @Getter
    @Setter
    private PersonDetails personDetails;

    public static ProjectMemberDetails fromProjectMemberAndPersonDetails(ProjectMember projectMember, PersonDetails personDetails) {
        ProjectMemberDetails pmd = new ProjectMemberDetails();
        pmd.setRoleInProject(projectMember.getRole());
        pmd.setPersonDetails(personDetails);
        return pmd;
    }
}
