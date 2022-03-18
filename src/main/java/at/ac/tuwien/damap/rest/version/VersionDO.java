package at.ac.tuwien.damap.rest.version;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionDO {

    private Long id;
    private Long dmpId;
    private Long revisionNumber;
    private String versionName;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date versionDate;
}
