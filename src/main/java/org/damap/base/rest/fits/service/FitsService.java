package org.damap.base.rest.fits.service;

import org.damap.base.domain.Dataset;
import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;
import org.damap.base.rest.fits.mapper.FitsMapper;
import org.damap.base.rest.fits.mapper.MultipartBodyMapper;
import edu.harvard.fits.Fits;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@JBossLog
public class FitsService {

    @Inject
    @RestClient
    FitsRestService fitsRestService;

    public Dataset analyseFile(MultipartBodyDO multipartBodyDO) {
        log.trace("Analyse File");
        MultipartBodyDTO multipartBodyDTO = MultipartBodyMapper.mapAtoB(multipartBodyDO, new MultipartBodyDTO());
        Fits fits;
        // TODO: Handle JAXB Exceptions
        fits = fitsRestService.analyseFile(multipartBodyDTO);
        return FitsMapper.mapAtoB(fits, new Dataset());
    }
}
