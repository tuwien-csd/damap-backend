package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * Damap compatible representation of persons
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDO {

    @JsonbProperty("id")
    private String universityId;
    private IdentifierDO personId;
    private String firstName;
    private String lastName;
    private String mbox;

}
