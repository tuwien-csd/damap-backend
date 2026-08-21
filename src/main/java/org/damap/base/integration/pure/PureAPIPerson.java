package org.damap.base.integration.pure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;

/**
 * This class is a simplified view of the Person object in Pure.
 *
 * @see <a href="https://api.elsevierpure.com/ws/api/rapidoc.html">Elsevier Pure API doc</a>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PureAPIPerson {
  @JsonProperty String uuid;

  @JsonProperty PureAPIName name;

  @JsonProperty String orcid;

  @JsonProperty Boolean orcidAuthenticated;

  @JsonProperty List<PureAPIPersonOrganizationAssociation> staffOrganizationAssociations;

  @JsonProperty List<PureAPIPersonOrganizationAssociation> studentOrganizationAssociations;

  @JsonProperty PureAPIUserRef user;

  ContributorDO toContributor() {
    ContributorDO contributor = new ContributorDO();

    IdentifierDO identifier = new IdentifierDO();
    if (Boolean.TRUE.equals(orcidAuthenticated) && orcid != null && !orcid.isEmpty()) {
      identifier.setIdentifier(orcid);
      identifier.setType(EIdentifierType.ORCID);
    } else {
      identifier.setIdentifier(uuid);
      identifier.setType(EIdentifierType.OTHER);
    }
    contributor.setUniversityId(uuid);
    contributor.setPersonId(identifier);
    if (name != null) {
      contributor.setFirstName(name.getFirstName());
      contributor.setLastName(name.getLastName());
    }
    contributor.setMbox(firstAvailableAssociationEmail());
    return contributor;
  }

  // First email from staff, else from student, else null. Staff wins over student per Pure
  // convention.
  String firstAvailableAssociationEmail() {
    String staffEmail = firstAssociationEmail(staffOrganizationAssociations);
    if (staffEmail != null) {
      return staffEmail;
    }
    return firstAssociationEmail(studentOrganizationAssociations);
  }

  private static String firstAssociationEmail(
      List<PureAPIPersonOrganizationAssociation> associations) {
    if (associations == null) {
      return null;
    }
    for (PureAPIPersonOrganizationAssociation association : associations) {
      if (association == null || association.getEmails() == null) {
        continue;
      }
      for (PureAPIClassifiedValue email : association.getEmails()) {
        if (email != null && email.getValue() != null && !email.getValue().isBlank()) {
          return email.getValue();
        }
      }
    }
    return null;
  }
}
