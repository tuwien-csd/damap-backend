package org.damap.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.envers.Audited;

@Data
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"dmp", "distributionList"})
@ToString(exclude = {"dmp", "distributionList"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@Audited
public class Host extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dmp_id")
  private Dmp dmp;

  private String title;

  @OneToMany(
      mappedBy = "host",
      cascade = {CascadeType.ALL},
      orphanRemoval = true)
  private List<Distribution> distributionList = new ArrayList<>();
}
