package at.ac.tuwien.rest.tiss.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingSource {

    @Getter
    @Setter
    private FundingType fundingType;

    @Getter
    @Setter
    private FunderName funderName;

    @Getter
    @Setter
    private ProgramInfoWrapper program;
}
