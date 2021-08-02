package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.DatasetDO;
import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.damap.rest.mapper.DatasetDOMapper;
import at.ac.tuwien.rest.fits.service.FitsService;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/fits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
@JBossLog
public class FitsResource {

    @Inject
    FitsService fitsService;

    @POST
    @Path("/examine")
    public DatasetDO examine(@MultipartForm MultipartBodyDO data) {
        log.info("Analyse file");
        return DatasetDOMapper.mapEntityToDO(fitsService.analyseFile(data), new DatasetDO());
    }
}
