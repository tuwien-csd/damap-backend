package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.damap.base.annotations.gdpr.Gdpr;
import org.damap.base.annotations.gdpr.GdprExtended;
import org.damap.base.annotations.gdpr.GdprKey;

/** Consent class. */
@Gdpr
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Consent extends PanacheEntity {

  @GdprKey
  @Column(name = "university_id")
  private String universityId;

  @GdprExtended
  @Column(name = "consent_given")
  private Boolean consentGiven;

  @GdprExtended
  @Column(name = "given_date")
  private Date givenDate;
}
