package org.damap.base.rest.persons.orcid.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.damap.base.rest.persons.orcid.models.base.ORCIDAffiliation;
import lombok.Data;

@Data
public class ORCIDEmploymentSummary implements ORCIDAffiliationSummary {
    @JsonProperty(value = "employment-summary")
    ORCIDAffiliation summary;
}
