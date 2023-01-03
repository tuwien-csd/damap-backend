package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorORCIDExpandedSearch {

    @JsonProperty(value = "orcid-id")
    String orcidId;

    @JsonProperty(value = "given-names")
    String givenNames;

    @JsonProperty(value = "family-names")
    String familyNames;

    @JsonProperty(value = "email")
    List<String> emails;

    @JsonProperty(value = "institution-name")
    List<String> affiliations;
}
