package at.ac.tuwien.damap.rest.administration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ConsentDO {

    private Long id;
    private String universityId;
    private Boolean consentGiven;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date givenDate;

}
