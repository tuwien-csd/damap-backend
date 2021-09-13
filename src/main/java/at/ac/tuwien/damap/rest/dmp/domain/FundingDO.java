package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EFundingState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingDO {

    private Long id;
    private IdentifierDO funder_id;
    private IdentifierDO grant_id;
    private EFundingState funding_status;
}
