package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.DmpDO;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.service.SaveDmpWrapper;
import at.ac.tuwien.damap.rest.service.DmpService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/dmps")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class DataManagementPlanResource {

    @Inject
    JsonWebToken jsonWebToken;

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

    @GET
    @Path("/person/{id}")
    @RolesAllowed("Damap Admin")
    public DmpDO getDmpByPerson(@PathParam String id) {
        log.info("Return dmp for person id: " + id);
        return dmpService.getDmpById(Long.parseLong(id));
    }

    // USER

    @GET
    @Path("/list")
    public List<DmpListItemDO> getDmpList() {
        log.info("Return dmp list for user");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        return dmpService.getDmpListByPersonId(personId);
    }


    @GET
    @Path("/subordinates")
    @RolesAllowed("user")
    public List<DmpListItemDO> getDmpsSubordinates() {
        log.info("Return dmp list for subordinates");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        // TODO: Service stub
        return dmpService.getDmpListByPersonId(personId);
    }

    @GET
    @Path("/{id}")
    public DmpDO getDmpById(@PathParam String id) {
        log.info("Return dmp with id: " + id);
        return dmpService.getDmpById(Long.parseLong(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public DmpDO saveDmp(DmpDO dmpDO) {
        log.info("Save dmp");
        String personId = this.getPersonId();
        SaveDmpWrapper dmpWrapper = new SaveDmpWrapper();
        dmpWrapper.setDmp(dmpDO);
        dmpWrapper.setEdited_by(personId);
        return dmpService.save(dmpWrapper);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public DmpDO updateDmp(@PathParam String id, DmpDO dmpDO) {
        log.info("Update dmp with id: " + id);
        String personId = this.getPersonId();
        SaveDmpWrapper dmpWrapper = new SaveDmpWrapper();
        dmpWrapper.setDmp(dmpDO);
        dmpWrapper.setEdited_by(personId);
        return dmpService.save(dmpWrapper);
    }

    private String getPersonId() {
        if (jsonWebToken.claim("tissID").isEmpty()) {
            throw new AuthenticationFailedException("Tiss ID is missing.");
        }
        return String.valueOf(jsonWebToken.claim("tissID").get());
    }
}
