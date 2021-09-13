package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierDO {

    private String identifier;
    private EIdentifierType type;
}
