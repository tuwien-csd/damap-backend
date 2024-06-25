package org.damap.base.r3data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import org.damap.base.enums.EIdentifierType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDetails {

  private String id;
  private String name;
  private List<String> repositoryIdentifier;
  private String repositoryURL;
  private List<String> repositoryLanguages;
  private String description;
  private Boolean versioning;
  private List<String> contentTypes;
  private List<String> metadataStandards;
  private List<EIdentifierType> pidSystems;
}
