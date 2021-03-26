package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.rest.domain.ContributorDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;

public class ContributorDOMapper {

    public static void mapEntityToDO(Contributor contributor, ContributorDO contributorDO) {

        contributorDO.setRole(contributor.getContributorRole());

        if (contributor.getContributor() != null) {
            PersonDO personDO = new PersonDO();
            PersonDOMapper.mapEntityToDO(contributor.getContributor(), personDO);
            contributorDO.setPerson(personDO);
        }
    }

    public static void mapDOtoEntity(ContributorDO contributorDO, Contributor contributor){

        contributor.setContributorRole(contributorDO.getRole());

        if (contributorDO.getPerson() != null) {
            Person person = new Person();
            if (contributor.getContributor() != null)
                person = contributor.getContributor();
            PersonDOMapper.mapDOtoEntity(contributorDO.getPerson(), person);
            contributor.setContributor(person);
        } else
            contributor.setContributor(null);
    }
}
