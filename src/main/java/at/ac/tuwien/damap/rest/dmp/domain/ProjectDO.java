package at.ac.tuwien.damap.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDO {

    private Long id;
    @Size(max = 255)
    private String acronym;
    @Size(max = 255)
    private String universityId;
    private String description;
    @Size(max = 255)
    private String title;
    private FundingDO funding;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date end;

    //indicates if this project is already related to a dmp
    private boolean dmpExists = false;

    private boolean funderSupported = false;

}
