package at.ac.tuwien.damap.rest.administration.service;
import at.ac.tuwien.damap.domain.Consent;
import at.ac.tuwien.damap.repo.ConsentRepo;
import at.ac.tuwien.damap.rest.administration.domain.ConsentDO;
import at.ac.tuwien.damap.rest.administration.mapper.ConsentDOMapper;
import lombok.extern.jbosslog.JBossLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@JBossLog
public class ConsentService {

    @Inject
    ConsentRepo consentRepo;

    public ConsentDO getConsentByUser(String universityId) {
        return ConsentDOMapper.mapEntityToDO(consentRepo.getConsentByUniversityId(universityId), new ConsentDO());
    }

    @Transactional
    public ConsentDO create(ConsentDO consentDO) {
        Consent consent = ConsentDOMapper.mapDOtoEntity(consentDO, new Consent());
        consent.persistAndFlush();
        return getConsentByUser(consent.getUniversityId());
    }

    @Transactional
    public ConsentDO update(ConsentDO consentDO) {
        Consent consent = consentRepo.getConsentByUniversityId(consentDO.getUniversityId());
        ConsentDOMapper.mapDOtoEntity(consentDO, consent);
        consent.persistAndFlush();
        return getConsentByUser(consent.getUniversityId());
    }
}
