package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ContributorORCIDExpandedSearch {
    @JsonAlias({ "orcid-id" })
    String orcidId;

    @JsonAlias({ "given-names" })
    String givenNames;

    @JsonAlias({ "family-names" })
    String familyNames;

    @JsonAlias({ "email" })
    List<String> emails;

    @JsonAlias({ "institution-name" })
    List<String> affiliations;
}
