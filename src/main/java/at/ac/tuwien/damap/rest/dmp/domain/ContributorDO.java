package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EContributorRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Damap compatible representation of persons
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributorDO {

    private Long id;
    private EContributorRole role;
    private PersonDO person;
}
