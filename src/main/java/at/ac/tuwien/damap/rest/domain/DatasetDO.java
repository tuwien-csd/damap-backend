package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetDO {

    private Long id;
    private String title;
    private String type;
    private String size;
    private String comment;
    private Boolean personalData;
    private Boolean sensitiveData;
    private Boolean legalRestrictions;
    private String license;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date startDate;
    private String referenceHash;
    private String dataAccess;
}
