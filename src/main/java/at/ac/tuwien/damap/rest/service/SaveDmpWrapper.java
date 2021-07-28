package at.ac.tuwien.damap.rest.service;

import at.ac.tuwien.damap.rest.domain.DmpDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveDmpWrapper {

    private String edited_by;
    private DmpDO dmp;
}
