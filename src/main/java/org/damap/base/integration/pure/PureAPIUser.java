package org.damap.base.integration.pure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// Simplified view of the Pure User object. On some Pure setups the email
// only lives here, not on the Person's org associations.
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PureAPIUser {
  @JsonProperty String uuid;

  @JsonProperty String email;
}
