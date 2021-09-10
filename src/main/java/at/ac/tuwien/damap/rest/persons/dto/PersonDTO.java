package at.ac.tuwien.damap.rest.persons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO {


    @JsonbProperty("tiss_id")
    private String id;

    @JsonbProperty("first_name")
    private String firstName;

    @JsonbProperty("last_name")
    private String lastName;

    private String gender;

    private String orcid;

    @JsonbProperty("preceding_titles")
    private String precedingTitles;

    @JsonbProperty("postpositioned_titles")
    private String postpositionedTitles;

    @JsonbProperty("main_email")
    private String mainEmail;

    @JsonbProperty("other_emails")
    private List<String> otherEmails;

    @JsonbProperty("main_phone_number")
    private String mainPhoneNumber;

    @JsonbProperty("employee")
    private List<EmploymentDTO> employmentList;
}
