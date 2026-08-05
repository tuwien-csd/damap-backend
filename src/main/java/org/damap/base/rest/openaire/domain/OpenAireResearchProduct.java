package org.damap.base.rest.openaire.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/** Subset of an OpenAIRE research product used to populate a DAMAP dataset. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAireResearchProduct {

  private String id;
  private String type;
  private String mainTitle;
  private List<String> descriptions = new ArrayList<>();
  private String publicationDate;
  private List<String> formats = new ArrayList<>();
  private OpenAireAccessRight bestAccessRight;
  private String size;
  private List<OpenAirePid> pids = new ArrayList<>();
  private List<OpenAireInstance> instances = new ArrayList<>();
}
