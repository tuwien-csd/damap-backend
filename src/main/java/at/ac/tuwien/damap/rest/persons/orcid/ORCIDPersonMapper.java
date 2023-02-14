package at.ac.tuwien.damap.rest.persons.orcid;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ORCIDPersonMapper {

    public ContributorDO mapEntityToDO(ORCIDPerson orcidPerson, ContributorDO contributorDO) {

        contributorDO.setId(null);
        contributorDO.setFirstName(orcidPerson.getGivenNames());
        contributorDO.setLastName(orcidPerson.getFamilyNames());

        String firstMail = orcidPerson.getEmails().isEmpty() ? null : orcidPerson.getEmails().get(0);
        contributorDO.setMbox(firstMail);

        String firstAffiliation = orcidPerson.getAffiliations().isEmpty() ? null
                : orcidPerson.getAffiliations().get(0);
        contributorDO.setAffiliation(firstAffiliation);

        IdentifierDO identifierContributorDO = new IdentifierDO() {
            {
                setIdentifier(orcidPerson.orcidId);
                setType(EIdentifierType.ORCID);
            }
        };
        contributorDO.setPersonId(identifierContributorDO);

        return contributorDO;
    }
}
