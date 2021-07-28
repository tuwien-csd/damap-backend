package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

/**
 * Damap compatible representation of persons
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDO {

    private Long id;
    private String universityId;
    private IdentifierDO personId;
    private String firstName;
    private String lastName;
    private String mbox;

}
