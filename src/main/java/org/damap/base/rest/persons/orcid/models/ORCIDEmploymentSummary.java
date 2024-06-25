package org.damap.base.rest.persons.orcid.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.damap.base.rest.persons.orcid.models.base.ORCIDAffiliation;

@Data
public class ORCIDEmploymentSummary implements ORCIDAffiliationSummary {
  @JsonProperty(value = "employment-summary")
  ORCIDAffiliation summary;
}
