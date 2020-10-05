package at.ac.tuwien.rest.tiss.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Institute {

    @Getter
    @Setter
    private String tid;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    @JsonbProperty("projectCoordinator")
    private Boolean isProjectCoordinator;

    @Getter
    @Setter
    private ProjectMemberWrapper projectMembers;
}
