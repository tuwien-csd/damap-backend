package org.damap.base.integration.pure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

// A person's org association (staff, student, etc.). We only need the emails array here.
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PureAPIPersonOrganizationAssociation {
  @JsonProperty List<PureAPIClassifiedValue> emails;
}
