package org.damap.base.rest.access.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.damap.base.enums.EFunctionRole;
import org.damap.base.rest.dmp.domain.ContributorDO;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessDO extends ContributorDO {

  @NotNull @Positive private long dmpId;

  @NotNull private EFunctionRole access;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private Date start;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
  private Date until;
}
