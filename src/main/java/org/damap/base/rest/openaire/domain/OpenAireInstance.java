package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** Materialization of an OpenAIRE research product. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAireInstance {

  private String type;
  private String license;
  private OpenAireAccessRight accessRight;
  private String publicationDate;
}
