package at.ac.tuwien.rest.madmp.dto;

import at.ac.tuwien.damap.enums.EFundingState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpFunding {

    private MaDmpIdentifier funder_id;
    private String funding_status;
    private MaDmpIdentifier grant_id;
}
