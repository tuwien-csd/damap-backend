package at.ac.tuwien.damap.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostDO {

    @JsonbProperty("id")
    private String hostId;
    private String title;
    private Date date;
    //referenceHashes
    private List<String> datasets;
}
