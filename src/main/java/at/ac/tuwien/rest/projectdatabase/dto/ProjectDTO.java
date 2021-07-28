package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO {

    private String id;
    private String description;
    private String title;
    private String start;
    private String end;
    private FundingDTO funding;
    private List<OrgunitDTO> orgUnitList;
    private List<ProjectMemberDTO> projectMembers;
}
