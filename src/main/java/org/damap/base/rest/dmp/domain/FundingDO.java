package org.damap.base.rest.dmp.domain;

import org.damap.base.enums.EFundingState;
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
