package at.ac.tuwien.damap.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDO {

    private Long id;
    private String acronym;
    private String universityId;
    private String description;
    private String title;
    private FundingDO funding;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date start;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date end;

    //indicates if this project is already related to a dmp
    private boolean dmpExists = false;

}
