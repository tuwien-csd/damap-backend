package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO {

    private long id;
    private String description;
    private String title;
    private String start;
    private String end;
    private FundingDTO funding;
    private List<OrgunitDTO> orgUnitList;
    private List<ProjectMemberDTO> projectMembers;
}
