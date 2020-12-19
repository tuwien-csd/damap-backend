package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDO {

    private long id;
    private String description;
    private String title;
    private Date start;
    private Date end;
    private FundingDO funding;
}
