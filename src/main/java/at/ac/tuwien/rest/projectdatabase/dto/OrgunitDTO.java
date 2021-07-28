package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgunitDTO {

    private long id;
    private String code;
}
