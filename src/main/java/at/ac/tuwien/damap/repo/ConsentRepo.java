package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Consent;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsentRepo implements PanacheRepository<Consent> {

    public Consent getConsentByUniversityId(String universityId) {
        return find("select consent from Consent consent" +
                    " where consent.universityId = :universityId ",
                    Parameters.with("universityId", universityId)).firstResult();
    }
}
