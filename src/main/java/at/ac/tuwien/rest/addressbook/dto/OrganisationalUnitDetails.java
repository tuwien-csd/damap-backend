package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationalUnitDetails extends OrganisationalUnit {

    @JsonbProperty("parent_org_ref")
    private OrganisationalUnit parentOrganisationalUnit;

    @JsonbProperty("child_orgs_refs")
    private List<OrganisationalUnit> childOrganisationalUnits;
}
