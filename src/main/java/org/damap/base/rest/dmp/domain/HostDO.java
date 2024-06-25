package org.damap.base.rest.dmp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostDO {

  private Long id;

  @Size(max = 255)
  private String title;

  // referenceHashes
  private List<String> datasets = new ArrayList<>();
}
