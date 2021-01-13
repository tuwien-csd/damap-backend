package at.ac.tuwien.rest.projectdatabase.dto;

import at.ac.tuwien.damap.enums.EFundingState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingDTO {

    private IdentifierDTO funder_id;
    private IdentifierDTO grant_id;
    private EFundingState funding_status;
}
