package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.rest.domain.ContributorDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;

public class ContributorDOMapper {

    public static void mapEntityToDO(Contributor contributor, ContributorDO contributorDO) {

        if (contributor.getContributorRole() != null)
            contributorDO.setRole(contributor.getContributorRole().getRole());

        if (contributor.getContributor() != null) {
            PersonDO personDO = new PersonDO();
            PersonDOMapper.mapEntityToDO(contributor.getContributor(), personDO);
            contributorDO.setPerson(personDO);
        }
    }

    public static void mapDOtoEntity(ContributorDO contributorDO, Contributor contributor){

        contributor.setContributorRole(EContributorRole.getByRole(contributorDO.getRole()));

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
