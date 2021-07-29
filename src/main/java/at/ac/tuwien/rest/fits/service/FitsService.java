package at.ac.tuwien.rest.fits.service;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;
import at.ac.tuwien.rest.fits.mapper.FitsMapper;
import at.ac.tuwien.rest.fits.mapper.MultipartBodyMapper;
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
        MultipartBodyDTO multipartBodyDTO = new MultipartBodyDTO();
        MultipartBodyMapper.mapAtoB(multipartBodyDO, multipartBodyDTO);
        Fits fits;
        Dataset dataset = new Dataset();
        // TODO: Handle JAXB Exceptions
        fits = fitsRestService.analyseFile(multipartBodyDTO);
        FitsMapper.mapAtoB(fits, dataset);
        return dataset;
    }
}
