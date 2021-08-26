package at.ac.tuwien.damap.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
