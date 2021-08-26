package at.ac.tuwien.damap.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostDO {

    private Long id;
    private String hostId;
    private String title;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
    //referenceHashes
    private List<String> datasets;
}
