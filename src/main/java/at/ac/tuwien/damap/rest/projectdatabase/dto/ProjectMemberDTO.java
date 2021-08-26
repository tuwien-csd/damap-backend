package at.ac.tuwien.damap.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberDTO {

    private long id;
    private String role;
}
