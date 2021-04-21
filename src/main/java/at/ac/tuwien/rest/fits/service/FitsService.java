package at.ac.tuwien.rest.fits.service;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;
import at.ac.tuwien.rest.fits.dto.generated.Fits;
import at.ac.tuwien.rest.fits.mapper.FitsMapper;
import at.ac.tuwien.rest.fits.mapper.MultipartBodyMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FitsService {

    private static final Logger log = LoggerFactory.getLogger(FitsService.class);

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
