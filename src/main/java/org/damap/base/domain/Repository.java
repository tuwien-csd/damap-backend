package org.damap.base.domain;

import jakarta.persistence.*;
import jakarta.persistence.Access;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

/** Repository class. */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("REPOSITORY")
@Access(AccessType.FIELD)
@Audited
public class Repository extends Host {

  @Column(name = "repository_id")
  private String repositoryId;
}
