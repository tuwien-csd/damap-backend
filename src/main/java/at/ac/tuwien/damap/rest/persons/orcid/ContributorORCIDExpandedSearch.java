package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorORCIDExpandedSearch {
    @JsonbProperty(value = "orcid-id")
    String orcidId;

    @JsonbProperty(value = "given-names")
    String givenNames;

    @JsonbProperty(value = "family-names")
    String familyNames;

    @JsonbProperty(value = "email")
    List<String> emails;

    @JsonbProperty(value = "institution-name")
    List<String> affiliations;
}
