package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.damap.base.annotations.gdpr.*;
import org.damap.base.enums.EFunctionRole;
import org.hibernate.envers.Audited;

@Gdpr
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Table(name = "access_management")
public class Access extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  @GdprContext(properties = {"id", "project.title"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dmp_id", nullable = false, updatable = false)
  private Dmp dmp;

  @GdprKey
  @Column(name = "university_id")
  private String universityId;

  @GdprBase
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "identifier_id", updatable = false)
  private Identifier personIdentifier;

  @GdprExtended
  @Enumerated(EnumType.STRING)
  private EFunctionRole role;

  @Column(name = "start_date")
  private Date start;

  @Column(name = "until_date")
  private Date until;
}
