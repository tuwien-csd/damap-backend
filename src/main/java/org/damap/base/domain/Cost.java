package org.damap.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.*;
import org.damap.base.enums.ECostType;
import org.hibernate.envers.Audited;

/** Cost class. */
@Data
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"dmp"})
@ToString(exclude = {"dmp"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class Cost extends PanacheEntity {

  @Version
  @Setter(AccessLevel.NONE)
  private long version;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dmp_id")
  private Dmp dmp;

  private String title;

  @Column(name = "cost_value")
  private Float value;

  @Column(name = "currency_code")
  private String currencyCode; // controlled vocabulary: ISO 4217

  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "cost_type")
  private ECostType type;

  @Column(name = "custom_type")
  private String customType;
}
