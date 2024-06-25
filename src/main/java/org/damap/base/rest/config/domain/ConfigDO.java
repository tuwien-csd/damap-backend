package org.damap.base.rest.config.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigDO {

  private String authUrl;
  private String authClient;
  private String authScope;
  private String authUser;
  private String env;
  private List<ServiceConfig> personSearchServiceConfigs;
  private boolean fitsServiceAvailable;
}
