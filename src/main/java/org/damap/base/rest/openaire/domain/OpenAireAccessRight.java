package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** Access-right vocabulary value returned by OpenAIRE. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAireAccessRight {

  private String code;
  private String label;
}
