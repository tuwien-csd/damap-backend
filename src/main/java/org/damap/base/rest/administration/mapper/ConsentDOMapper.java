package org.damap.base.rest.administration.mapper;
import org.damap.base.domain.Consent;
import org.damap.base.rest.administration.domain.ConsentDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConsentDOMapper {

    public ConsentDO mapEntityToDO(Consent consent, ConsentDO consentDO) {
        consentDO.setUniversityId(consent.getUniversityId());
        consentDO.setConsentGiven(consent.getConsentGiven());
        consentDO.setGivenDate(consent.getGivenDate());

        return consentDO;
    }

    public Consent mapDOtoEntity(ConsentDO consentDO, Consent consent){
        consent.setUniversityId(consentDO.getUniversityId());
        consent.setConsentGiven(consentDO.getConsentGiven());
        consent.setGivenDate(consentDO.getGivenDate());

        return consent;
    }
}