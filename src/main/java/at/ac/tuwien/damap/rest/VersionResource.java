package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.version.VersionDO;
import at.ac.tuwien.damap.rest.version.VersionService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/versions")
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class VersionResource {


    @Inject
    SecurityService securityService;

    @Inject
    AccessValidator accessValidator;

    @Inject
    VersionService versionService;

    @GET
    @Path("/list/{id}")
    public List<VersionDO> getDmpVersions(@PathParam String id) {
        log.debug("Return dmp versions for dmp with id: " + id);
        String personId = this.getPersonId();
        long dmpId = Long.parseLong(id);
        if(!accessValidator.canViewDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }
        return versionService.getDmpVersions(dmpId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public VersionDO saveVersion(VersionDO versionDO) {
        log.info("Save/update version");
        String personId = this.getPersonId();
        if(!accessValidator.canEditDmp(versionDO.getDmpId(), personId)){
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
