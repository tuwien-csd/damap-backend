package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EFundingState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingDO {

    private Long id;
    private String fundingName;
    private String fundingProgram;
    private IdentifierDO funderId;
    private IdentifierDO grantId;
    private EFundingState fundingStatus;
}
