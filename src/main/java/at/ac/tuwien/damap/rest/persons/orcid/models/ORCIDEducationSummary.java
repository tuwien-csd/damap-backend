package at.ac.tuwien.damap.rest.persons.orcid.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import at.ac.tuwien.damap.rest.persons.orcid.models.base.ORCIDAffiliation;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDEducationSummary implements ORCIDAffiliationSummary {

    @JsonSetter(value = "education-summary")
    ORCIDAffiliation summary;
}
