package at.ac.tuwien.rest.fits.service;

import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;
import at.ac.tuwien.rest.fits.mapper.FitsDTOMapper;
import edu.harvard.hul.ois.xml.ns.fits.fits_output.Fits;
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

    public Fits analyseFile(MultipartBodyDO data) {
        log.trace("Analyse File");
        MultipartBodyDTO fitsData = new MultipartBodyDTO();
        FitsDTOMapper.mapAtoB(data, fitsData);
        return fitsRestService.analyseFile(fitsData);
    }
}
