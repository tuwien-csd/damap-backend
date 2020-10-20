package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingProgramInfo {

    @Getter
    @Setter
    private String infoTypeCode;

    @Getter
    @Setter
    @JsonbProperty("infoValue")
    private FundingProgramInfoValue value;

    @Getter
    @Setter
    @JsonbProperty("infoTag")
    private FundingProgramInfoTag tag;
}
