package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.rest.domain.ContributorDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;

public class ContributorDOMapper {

    public static void mapAtoB(Contributor contributor, ContributorDO contributorDO) {

        contributorDO.setId(contributor.id);
        contributorDO.setContributorRole(contributor.getContributorRole());

        PersonDO personDO = new PersonDO();
        PersonDOMapper.mapAtoB(contributor.getContributor(), personDO);
        contributorDO.setPersonDO(personDO);
    }
}
