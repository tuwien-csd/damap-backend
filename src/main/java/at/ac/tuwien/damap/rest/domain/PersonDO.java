package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Damap compatible representation of persons
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDO {

    private Long id;

    private PersonIdDO personId;

    private String firstName;

    private String lastName;

    private String mbox;

}
