package org.damap.base.rest.administration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Data;

/** ConsentDO class. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentDO {

  private Long id;
  private String universityId;
  private Boolean consentGiven;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private Date givenDate;
}
