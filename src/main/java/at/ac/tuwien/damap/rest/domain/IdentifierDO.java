package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.damap.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierDO {

    private String identifier;
    private EIdentifierType type;
}
