package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDExpandedSearchResult {

    @JsonbProperty(value = "expanded-result")
    private List<ContributorORCIDExpandedSearch> persons;

    @JsonbProperty(value = "num-found")
    private long numFound;
}
