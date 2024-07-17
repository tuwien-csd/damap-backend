package org.damap.base.domain;

import jakarta.persistence.*;
import jakarta.persistence.Access;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

/** Storage class. */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("STORAGE")
@Access(AccessType.FIELD)
@Audited
public class Storage extends Host {

  @ManyToOne
  @JoinColumn(name = "internal_storage_id")
  private InternalStorage internalStorageId;
}
