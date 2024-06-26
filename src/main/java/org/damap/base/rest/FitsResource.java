package org.damap.base.rest;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.dmp.mapper.DatasetDOMapper;
import org.damap.base.rest.fits.service.FitsService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/api/fits")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
@JBossLog
public class FitsResource {

  @Inject FitsService fitsService;

  @POST
  @Path("/examine")
  public DatasetDO examine(@MultipartForm MultipartBodyDO data) {
    log.info("Analyse file");
    return DatasetDOMapper.mapEntityToDO(fitsService.analyseFile(data), new DatasetDO());
  }
}
