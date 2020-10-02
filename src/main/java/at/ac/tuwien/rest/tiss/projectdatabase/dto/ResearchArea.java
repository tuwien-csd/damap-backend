package at.ac.tuwien.rest.tiss.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResearchArea {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private ResearchAreaName name;
}
