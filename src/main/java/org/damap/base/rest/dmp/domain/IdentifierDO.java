package org.damap.base.rest.dmp.domain;

import org.damap.base.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierDO {

    @Size(max = 255)
    private String identifier;
    private EIdentifierType type;
}
