package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/** Response returned by the OpenAIRE Graph research-products search endpoint. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAireSearchResponse {

  private OpenAireSearchHeader header;
  private List<OpenAireResearchProduct> results = new ArrayList<>();
}
