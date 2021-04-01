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
public class ContributorDO {

    private String role;
    private PersonDO person;
}
