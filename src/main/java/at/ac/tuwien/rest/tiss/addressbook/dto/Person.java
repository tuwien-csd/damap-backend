package at.ac.tuwien.rest.tiss.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

/**
 * This class is used as a target for unmarshalling a client request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @Getter
    @Setter
    private String oid;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @JsonbProperty("orgs")
    private List<OrganisationalUnit> organisationalUnits;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
