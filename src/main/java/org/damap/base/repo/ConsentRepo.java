package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.damap.base.domain.Consent;

@ApplicationScoped
public class ConsentRepo implements PanacheRepository<Consent> {

  public Consent getConsentByUniversityId(String universityId) {
    return find(
            "select consent from Consent consent" + " where consent.universityId = :universityId ",
            Parameters.with("universityId", universityId))
        .firstResult();
  }
}
