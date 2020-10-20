package at.ac.tuwien.rest.addressbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDetails {

    @Getter
    @Setter
    @JsonbProperty("tiss_id")
    private Long id;

    @Getter
    @Setter
    @JsonbProperty("first_name")
    private String firstName;

    @Getter
    @Setter
    @JsonbProperty("last_name")
    private String lastName;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    @JsonbProperty("preceding_titles")
    private String precedingTitles;

    @Getter
    @Setter
    @JsonbProperty("postpositioned_titles")
    private String postpositionedTitles;

    @Getter
    @Setter
    @JsonbProperty("main_email")
    private String mainEmail;

    @Getter
    @Setter
    @JsonbProperty("other_emails")
    private List<String> otherEmails;

    @Getter
    @Setter
    @JsonbProperty("main_phone_number")
    private String mainPhoneNumber;

    @Getter
    @Setter
    @JsonbProperty("employee")
    private List<Employment> employmentList;
}
