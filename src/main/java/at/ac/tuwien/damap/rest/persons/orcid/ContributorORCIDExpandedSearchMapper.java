package at.ac.tuwien.damap.rest.persons.orcid;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContributorORCIDExpandedSearchMapper {

    public ContributorDO mapEntityToDO(ContributorORCIDExpandedSearch contributor, ContributorDO contributorDO) {

        contributorDO.setId(null);
        contributorDO.setFirstName(contributor.getGivenNames());
        contributorDO.setLastName(contributor.getFamilyNames());

        String firstMail = contributor.getEmails().isEmpty() ? null : contributor.getEmails().get(0);
        contributorDO.setMbox(firstMail);

        String firstAffiliation = contributor.getAffiliations().isEmpty() ? null : contributor.getAffiliations().get(0);
        contributorDO.setAffiliation(firstAffiliation);

        IdentifierDO identifierContributorDO = new IdentifierDO() {
            {
                setIdentifier(contributor.orcidId);
                setType(EIdentifierType.ORCID);
            }
        };
        contributorDO.setPersonId(identifierContributorDO);

        return contributorDO;
    }
}
