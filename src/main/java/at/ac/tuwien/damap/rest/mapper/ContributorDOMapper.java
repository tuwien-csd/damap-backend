package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.rest.domain.ContributorDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContributorDOMapper {

    public ContributorDO mapEntityToDO(Contributor contributor, ContributorDO contributorDO) {

        contributorDO.setId(contributor.id);

        if (contributor.getContributorRole() != null)
            contributorDO.setRole(contributor.getContributorRole().getRole());

        if (contributor.getContributor() != null) {
            PersonDO personDO = new PersonDO();
            PersonDOMapper.mapEntityToDO(contributor.getContributor(), personDO);
            contributorDO.setPerson(personDO);
        }

        return contributorDO;
    }

    public Contributor mapDOtoEntity(ContributorDO contributorDO, Contributor contributor){

        if (contributorDO.getId() != null)
            contributor.id = contributorDO.getId();
        contributor.setContributorRole(EContributorRole.getByRole(contributorDO.getRole()));

        if (contributorDO.getPerson() != null) {
            Person person = new Person();
            if (contributor.getContributor() != null)
                person = contributor.getContributor();
            PersonDOMapper.mapDOtoEntity(contributorDO.getPerson(), person);
            contributor.setContributor(person);
        } else
            contributor.setContributor(null);

        return contributor;
    }
}
