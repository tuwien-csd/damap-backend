package at.ac.tuwien.damap.rest.persons.orcid.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDExpandedSearchResult {

    @JsonProperty(value = "expanded-result")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ORCIDExpandedSearchPerson> persons;

    @JsonProperty(value = "num-found")
    private long numFound;
}
