package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

/** InternalStorage class. */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Access(AccessType.FIELD)
@Audited
@Table(name = "internal_storage")
public class InternalStorage extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  private String url;

  @Column(name = "storage_location")
  private String storageLocation;

  @Column(name = "backup_location")
  private String backupLocation;

  @Column(name = "active")
  private boolean active;
}
