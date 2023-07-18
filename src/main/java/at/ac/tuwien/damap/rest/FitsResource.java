package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.MultipartBodyDO;
import at.ac.tuwien.damap.rest.dmp.mapper.DatasetDOMapper;
import at.ac.tuwien.damap.rest.fits.service.FitsService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/fits")
@Authenticated
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
