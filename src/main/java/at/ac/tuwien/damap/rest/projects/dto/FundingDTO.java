package at.ac.tuwien.damap.rest.projects.dto;

import at.ac.tuwien.damap.enums.EFundingState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingDTO {

    private IdentifierDTO funder_id;
    private IdentifierDTO grant_id;
    private EFundingState funding_status;
}
