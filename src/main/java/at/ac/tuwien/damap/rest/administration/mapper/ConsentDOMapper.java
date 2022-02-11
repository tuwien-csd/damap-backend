package at.ac.tuwien.damap.rest.administration.mapper;
import at.ac.tuwien.damap.domain.Consent;
import at.ac.tuwien.damap.rest.administration.domain.ConsentDO;
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