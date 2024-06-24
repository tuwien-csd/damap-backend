package org.damap.base.rest.dmp.mapper;

import org.damap.base.domain.Contributor;
import org.damap.base.domain.Identifier;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContributorDOMapper {

    public ContributorDO mapEntityToDO(Contributor contributor, ContributorDO contributorDO) {

        contributorDO.setId(contributor.id);
        contributorDO.setFirstName(contributor.getFirstName());
        contributorDO.setLastName(contributor.getLastName());
        contributorDO.setMbox(contributor.getMbox());
        contributorDO.setUniversityId(contributor.getUniversityId());
        contributorDO.setAffiliation(contributor.getAffiliation());

        if (contributor.getPersonIdentifier() != null) {
            IdentifierDO identifierContributorDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(contributor.getPersonIdentifier(), identifierContributorDO);
            contributorDO.setPersonId(identifierContributorDO);
        }
        if (contributor.getAffiliationId() != null) {
            IdentifierDO affiliationIdentifierDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(contributor.getAffiliationId(), affiliationIdentifierDO);
            contributorDO.setAffiliationId(affiliationIdentifierDO);
        }
        if (contributor.getContributorRole() != null)
            contributorDO.setRole(contributor.getContributorRole());
        contributorDO.setContact(contributor.getContact() != null && contributor.getContact());

        return contributorDO;
    }

    public Contributor mapDOtoEntity(ContributorDO contributorDO, Contributor contributor){

        if (contributorDO.getId() != null)
            contributor.id = contributorDO.getId();
        contributor.setFirstName(contributorDO.getFirstName());
        contributor.setLastName(contributorDO.getLastName());
        contributor.setMbox(contributorDO.getMbox());
        contributor.setUniversityId(contributorDO.getUniversityId());
        contributor.setAffiliation(contributorDO.getAffiliation());

        if (contributorDO.getPersonId() != null) {
            Identifier identifierContributor = new Identifier();
            if (contributor.getPersonIdentifier() != null)
                identifierContributor = contributor.getPersonIdentifier();
            IdentifierDOMapper.mapDOtoEntity(contributorDO.getPersonId(), identifierContributor);
            contributor.setPersonIdentifier(identifierContributor);
        } else
            contributor.setPersonIdentifier(null);

        if (contributorDO.getAffiliationId() != null) {
            Identifier affiliationIdentifier = new Identifier();
            if (contributor.getAffiliationId() != null)
                affiliationIdentifier = contributor.getAffiliationId();
            IdentifierDOMapper.mapDOtoEntity(contributorDO.getAffiliationId(), affiliationIdentifier);
            contributor.setAffiliationId(affiliationIdentifier);
        } else
            contributor.setAffiliationId(null);

        contributor.setContact(contributorDO.isContact());
        contributor.setContributorRole(contributorDO.getRole());

        return contributor;
    }
}
