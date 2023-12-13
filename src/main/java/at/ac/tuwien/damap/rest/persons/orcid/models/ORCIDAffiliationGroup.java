package at.ac.tuwien.damap.rest.persons.orcid.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDAffiliationGroup<S extends ORCIDAffiliationSummary> {
    @JsonProperty(value = "affiliation-group")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    List<ORCIDGroup<S>> affiliationGroup;
}
