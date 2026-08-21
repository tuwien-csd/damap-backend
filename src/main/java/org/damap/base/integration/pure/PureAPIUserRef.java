package org.damap.base.integration.pure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// The user reference embedded in a Person response - just a uuid, use
// PureAPI.getUser to resolve it.
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class PureAPIUserRef {
  @JsonProperty String uuid;
}
