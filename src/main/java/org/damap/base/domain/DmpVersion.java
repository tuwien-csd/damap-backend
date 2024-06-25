package org.damap.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "dmp")
@Entity
@Table(name = "dmp_version")
public class DmpVersion extends PanacheEntity {

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dmp_id")
  @EqualsAndHashCode.Exclude
  private Dmp dmp;

  @Column(name = "version_date")
  private Date versionDate;

  @Column(name = "version_name")
  private String versionName;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude
  @JoinColumn(name = "revision_entity_id")
  private DamapRevisionEntity revisionEntity;
}
