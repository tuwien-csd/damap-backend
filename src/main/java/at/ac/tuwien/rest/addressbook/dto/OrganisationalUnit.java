package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationalUnit {

    @Getter
    @Setter
    @JsonbProperty("tiss_id")
    private Long id;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String number;

    @Getter
    @Setter
    @JsonbProperty("name_en")
    private String name;

    @Getter
    @Setter
    private String type;
}
