package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.damap.enums.EContributorRole;
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

    private Long id;

    private EContributorRole contributorRole;

    private PersonDO personDO;
}
