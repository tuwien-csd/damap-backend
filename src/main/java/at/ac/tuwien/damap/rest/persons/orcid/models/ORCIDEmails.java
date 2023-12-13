package at.ac.tuwien.damap.rest.persons.orcid.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import at.ac.tuwien.damap.rest.persons.orcid.models.base.ORCIDEmail;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ORCIDEmails {
    @JsonProperty
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    List<ORCIDEmail> email = List.of();
}
