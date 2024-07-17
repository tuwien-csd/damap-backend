package org.damap.base.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.damap.base.enums.EIdentifierType;

/** IdentifierDO class. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierDO {

  @Size(max = 255)
  private String identifier;

  private EIdentifierType type;
}
