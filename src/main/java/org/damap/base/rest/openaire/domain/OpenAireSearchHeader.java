package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** Metadata describing an OpenAIRE Graph search result page. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAireSearchHeader {

  private Long numFound;
  private Integer page;
  private Integer pageSize;
}
