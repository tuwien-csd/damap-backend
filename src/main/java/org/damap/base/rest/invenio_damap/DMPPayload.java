package org.damap.base.rest.invenio_damap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.damap.base.rest.madmp.dto.Dataset;

@Data
public class DMPPayload {
  @JsonProperty("dmp_id")
  private long dmpId;

  private Dataset dataset;
}
