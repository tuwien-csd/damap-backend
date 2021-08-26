package at.ac.tuwien.damap.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Damap compatible representation of persons
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorDO {

    private Long id;
    private String role;
    private PersonDO person;
}
