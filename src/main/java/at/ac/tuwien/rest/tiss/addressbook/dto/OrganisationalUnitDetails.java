package at.ac.tuwien.rest.tiss.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationalUnitDetails extends OrganisationalUnit {

    @Getter
    @Setter
    private String oid;

    @Getter
    @Setter
    @JsonbProperty("parent_org_ref")
    private OrganisationalUnit parentOrganisationalUnit;

    @Getter
    @Setter
    @JsonbProperty("child_orgs_refs")
    private List<OrganisationalUnit> childOrganisationalUnits;
}
