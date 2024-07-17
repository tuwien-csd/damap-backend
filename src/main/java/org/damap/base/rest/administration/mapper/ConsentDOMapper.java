package org.damap.base.rest.administration.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Consent;
import org.damap.base.rest.administration.domain.ConsentDO;

/** ConsentDOMapper class. */
@UtilityClass
public class ConsentDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param consent a {@link org.damap.base.domain.Consent} object
   * @param consentDO a {@link org.damap.base.rest.administration.domain.ConsentDO} object
   * @return a {@link org.damap.base.rest.administration.domain.ConsentDO} object
   */
  public ConsentDO mapEntityToDO(Consent consent, ConsentDO consentDO) {
    consentDO.setUniversityId(consent.getUniversityId());
    consentDO.setConsentGiven(consent.getConsentGiven());
    consentDO.setGivenDate(consent.getGivenDate());

    return consentDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param consentDO a {@link org.damap.base.rest.administration.domain.ConsentDO} object
   * @param consent a {@link org.damap.base.domain.Consent} object
   * @return a {@link org.damap.base.domain.Consent} object
   */
  public Consent mapDOtoEntity(ConsentDO consentDO, Consent consent) {
    consent.setUniversityId(consentDO.getUniversityId());
    consent.setConsentGiven(consentDO.getConsentGiven());
    consent.setGivenDate(consentDO.getGivenDate());

    return consent;
  }
}
