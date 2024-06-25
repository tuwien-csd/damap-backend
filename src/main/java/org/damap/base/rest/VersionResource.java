package org.damap.base.rest;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.version.VersionDO;
import org.damap.base.rest.version.VersionService;
import org.damap.base.security.SecurityService;
import org.damap.base.validation.AccessValidator;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/api/versions")
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class VersionResource {

  @Inject SecurityService securityService;

  @Inject AccessValidator accessValidator;

  @Inject VersionService versionService;

  @GET
  @Path("/list/{id}")
  public List<VersionDO> getDmpVersions(@PathParam String id) {
    log.debug("Return dmp versions for dmp with id: " + id);
    String personId = this.getPersonId();
    long dmpId = Long.parseLong(id);
    if (!accessValidator.canViewDmp(dmpId, personId)) {
      throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
    }
    return versionService.getDmpVersions(dmpId);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public VersionDO saveVersion(VersionDO versionDO) {
    log.info("Save/update version");
    String personId = this.getPersonId();
    if (!accessValidator.canEditDmp(versionDO.getDmpId(), personId)) {
      throw new ForbiddenException("Not authorized to access dmp with id " + versionDO.getDmpId());
    }
    return versionService.createOrUpdate(versionDO);
  }

  private String getPersonId() {
    if (securityService == null) {
      throw new AuthenticationFailedException("User ID is missing.");
    }
    return securityService.getUserId();
  }
}
