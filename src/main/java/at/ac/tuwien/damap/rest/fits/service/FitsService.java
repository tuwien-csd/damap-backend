package at.ac.tuwien.damap.rest.fits.service;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.rest.dmp.domain.MultipartBodyDO;
import at.ac.tuwien.damap.rest.fits.dto.MultipartBodyDTO;
import at.ac.tuwien.damap.rest.fits.mapper.FitsMapper;
import at.ac.tuwien.damap.rest.fits.mapper.MultipartBodyMapper;
import edu.harvard.fits.Fits;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
