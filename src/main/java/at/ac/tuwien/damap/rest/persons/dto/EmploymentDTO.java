package at.ac.tuwien.damap.rest.persons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmploymentDTO {

    private String function;

    @JsonbProperty("org_ref")
    private OrganisationalUnit organisationalUnit;
}
