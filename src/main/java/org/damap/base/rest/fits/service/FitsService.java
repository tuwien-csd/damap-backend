package org.damap.base.rest.fits.service;

import edu.harvard.fits.Fits;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.Dataset;
import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;
import org.damap.base.rest.fits.mapper.FitsMapper;
import org.damap.base.rest.fits.mapper.MultipartBodyMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/** FitsService class. */
@ApplicationScoped
@JBossLog
public class FitsService {

  @Inject @RestClient FitsRestService fitsRestService;

  /**
   * analyseFile.
   *
   * @param multipartBodyDO a {@link org.damap.base.rest.dmp.domain.MultipartBodyDO} object
   * @return a {@link org.damap.base.domain.Dataset} object
   */
  public Dataset analyseFile(MultipartBodyDO multipartBodyDO) {
    log.trace("Analyse File");
    MultipartBodyDTO multipartBodyDTO =
        MultipartBodyMapper.mapAtoB(multipartBodyDO, new MultipartBodyDTO());
    Fits fits;
    // TODO: Handle JAXB Exceptions
    fits = fitsRestService.analyseFile(multipartBodyDTO);
    return FitsMapper.mapAtoB(fits, new Dataset());
  }
}
