package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.service.FitsService;
import edu.harvard.hul.ois.xml.ns.fits.fits_output.Fits;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/fits")
public class FitsResource {

    private static final Logger log = LoggerFactory.getLogger(FitsResource.class);

    @Inject
    FitsService fitsService;

    @POST
    @Path("/examine")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Fits examine(@MultipartForm MultipartBodyDO data) {
        log.info("Analyse file");
        return fitsService.analyseFile(data);
    }
}
