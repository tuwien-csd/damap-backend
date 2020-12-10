package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

/**
 * This class is used as a target for unmarshalling a client request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @JsonbProperty("orgs")
    private List<OrganisationalUnit> organisationalUnits;
}
