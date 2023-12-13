package at.ac.tuwien.damap.rest.persons.orcid.models.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDEmail {
    @JsonProperty
    String email;

    @JsonProperty
    boolean verified;

    @JsonProperty
    boolean primary;
}
