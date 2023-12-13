package at.ac.tuwien.damap.rest.persons.orcid.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.tuwien.damap.rest.persons.orcid.models.base.ORCIDAffiliation;
import lombok.Data;

@Data
public class ORCIDEmploymentSummary implements ORCIDAffiliationSummary {
    @JsonProperty(value = "employment-summary")
    ORCIDAffiliation summary;
}
