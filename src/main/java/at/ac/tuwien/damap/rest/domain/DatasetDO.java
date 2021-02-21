package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetDO {

    private String title;
    private String type;
    private String size;
    private String comment;
    private boolean publish;
    private String license;
    private Date startDate;
    private String referenceHash;
}
