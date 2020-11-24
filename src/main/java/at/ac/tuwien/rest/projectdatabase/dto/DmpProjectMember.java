package at.ac.tuwien.rest.projectdatabase.dto;

import at.ac.tuwien.rest.addressbook.dto.DmpPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Damap compatible representation of project members
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmpProjectMember {

    @Getter
    @Setter
    private String roleInProject;

    @Getter
    @Setter
    private DmpPerson person;

    public static DmpProjectMember fromProjectMemberAndDmpPerson(ProjectMember projectMember, DmpPerson person) {
        DmpProjectMember pmd = new DmpProjectMember();
        pmd.setRoleInProject(projectMember.getRole());
        pmd.setPerson(person);
        return pmd;
    }
}
