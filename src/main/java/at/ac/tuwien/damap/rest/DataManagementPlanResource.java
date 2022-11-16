package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/dmps")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class DataManagementPlanResource {

    @Inject
    SecurityService securityService;

    @Inject
    AccessValidator accessValidator;

    @Inject
    DmpService dmpService;

    // ADMIN

    @GET
    @Path("/all")
    @RolesAllowed("Damap Admin")
    public List<DmpListItemDO> getAll() {
        log.info("Return all Dmps");
        return dmpService.getAll();
    }

    /*@GET
    @Path("/person/{personId}")
    @RolesAllowed("Damap Admin")
    public List<DmpListItemDO> getDmpListByPerson(@PathParam String personId) {
        log.info("Return dmp for person id: " + personId);
        return dmpService.getDmpListByPersonId(personId);
    }*/

    // USER

    @GET
    @Path("/list")
    public List<DmpListItemDO> getDmpList() {
        log.info("Return dmp list for user");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        return dmpService.getDmpListByPersonId(personId);
    }


    /*@GET
    @Path("/subordinates")
    @RolesAllowed("user")
    public List<DmpListItemDO> getDmpsSubordinates() {
        log.info("Return dmp list for subordinates");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        // TODO: Service stub
        return dmpService.getDmpListByPersonId(personId);
    }*/

    @GET
    @Path("/{id}")
    public DmpDO getDmpById(@PathParam String id) {
        log.info("Return dmp with id: " + id);
        String personId = this.getPersonId();
        long dmpId = Long.parseLong(id);
        if(!accessValidator.canViewDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }
        return dmpService.getDmpById(dmpId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public DmpDO saveDmp(@Valid DmpDO dmpDO) {
        log.info("Save dmp");
        String personId = this.getPersonId();
        return dmpService.create(dmpDO, personId);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public DmpDO updateDmp(@PathParam String id, @Valid DmpDO dmpDO) {
        log.info("Update dmp with id: " + id);
        String personId = this.getPersonId();
        long dmpId = Long.parseLong(id);
        if(!accessValidator.canEditDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }
        return dmpService.update(dmpDO);
    }

    @DELETE
    @Path("/{id}")
    public void deleteDmp(@PathParam String id) {
        log.info("Delete dmp with id: " + id);
        String personId = this.getPersonId();
        long dmpId = Long.parseLong(id);
        if(!accessValidator.canDeleteDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to delete dmp with id " + dmpId);
        }
        dmpService.delete(dmpId);
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }

    @GET
    @Path("/{id}/{revision}")
    public DmpDO getDmpByIdAndRevision(@PathParam String id, @PathParam long revision) {
        log.info("Return dmp with id: " + id + " and revision number: " + revision);
        String personId = this.getPersonId();
        long dmpId = Long.parseLong(id);
        if(!accessValidator.canViewDmp(dmpId, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }
        return dmpService.getDmpByIdAndRevision(dmpId, revision);
    }
}
