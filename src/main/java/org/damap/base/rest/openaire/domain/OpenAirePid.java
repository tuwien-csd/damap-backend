package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** Persistent identifier attached to an OpenAIRE research product. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAirePid {

  private String scheme;
  private String value;
}
