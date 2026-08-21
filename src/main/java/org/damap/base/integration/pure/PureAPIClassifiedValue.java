package org.damap.base.integration.pure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// One classified value entry (currently we only read email addresses via this).
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PureAPIClassifiedValue {
  @JsonProperty String value;
}
