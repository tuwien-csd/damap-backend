package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDExpandedSearchResult {

    @JsonProperty(value = "expanded-result")
    private List<ORCIDPerson> persons;

    @JsonProperty(value = "num-found")
    private long numFound;
}
