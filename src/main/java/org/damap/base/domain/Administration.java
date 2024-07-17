package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.damap.base.enums.EFunctionRole;
import org.hibernate.envers.Audited;

/** Administration class. */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Administration extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  @Column(name = "university_id")
  private String universityId;

  @Enumerated(EnumType.STRING)
  private EFunctionRole role;

  @Column(name = "start_date")
  private Date start;

  @Column(name = "until_date")
  private Date until;
}
