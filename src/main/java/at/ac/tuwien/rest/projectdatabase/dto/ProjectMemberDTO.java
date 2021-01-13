package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDTO {

    private long id;
    private String role;
}
