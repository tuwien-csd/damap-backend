package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employment {

    @Getter
    @Setter
    private String function;

    @Getter
    @Setter
    @JsonbProperty("org_ref")
    private OrganisationalUnit organisationalUnit;
}
