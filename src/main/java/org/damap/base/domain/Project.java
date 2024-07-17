package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.Length;
import org.hibernate.envers.Audited;

/** Project class. */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Project extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  @Column(name = "university_id")
  private String universityId;

  private String title;

  @Column(length = Length.LONG32)
  private String description;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "funding_id")
  private Funding funding;

  @Column(name = "project_start")
  private Date start;

  @Column(name = "project_end")
  private Date end;
}
