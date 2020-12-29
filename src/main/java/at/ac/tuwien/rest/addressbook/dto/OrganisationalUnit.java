package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationalUnit {

    @JsonbProperty("tiss_id")
    private Long id;

    private String code;

    private String number;

    @JsonbProperty("name_en")
    private String name;

    private String type;
}
