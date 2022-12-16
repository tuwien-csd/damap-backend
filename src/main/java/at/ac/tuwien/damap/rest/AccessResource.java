package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.access.domain.AccessDO;
import at.ac.tuwien.damap.rest.access.service.AccessService;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/access")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class AccessResource {

    @Inject
    AccessValidator accessValidator;
    @Inject
    AccessService accessService;

    @GET
    @Path("/dmps/{id}")
    public List<ContributorDO> getAccessForDmp(@PathParam("id") String id) {
        log.info("Return access list for dmp with id: " + id);
        long dmpId = Long.parseLong(id);
        if (!this.accessValidator.canViewAccess(dmpId)) {
            throw new ForbiddenException();
        }
        return accessService.getByDmpId(dmpId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public AccessDO create(@Valid AccessDO accessDO) {
        log.info("Create new access");
        // University id required for now, might change in the future
        if (accessDO.getUniversityId() == null) {
            throw new BadRequestException("University id is required");
        }

        if (!this.accessValidator.canCreateAccess(accessDO, accessService.canGetAccess(accessDO.getDmpId()))) {
            throw new ForbiddenException();
        }
        return accessService.create(accessDO);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        log.info("Delete access with id: " + id);
        long accessId = Long.parseLong(id);
        if (!this.accessValidator.canDeleteAccess(accessId)) {
            throw new ForbiddenException();
        }
        accessService.delete(accessId);
    }
}
