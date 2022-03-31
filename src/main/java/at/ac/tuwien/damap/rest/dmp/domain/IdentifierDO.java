package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierDO {

    @Size(max = 255)
    private String identifier;
    private EIdentifierType type;
}
