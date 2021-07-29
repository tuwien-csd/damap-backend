package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.DmpDO;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.service.SaveDmpWrapper;
import at.ac.tuwien.damap.rest.service.DmpService;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plans")
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class DataManagementPlanResource {

    @Inject
    DmpService dmpService;

    @GET
    @Path("/all")
    public List<DmpDO> getAll() {
        //TODO delete this method, it currently only exists for testing the DB
        log.info("Return all Dmps");
        return dmpService.getAll();
    }

    @GET
    @Path("/dmp-list/{personId}")
//    @RolesAllowed("user")
    public List<DmpListItemDO> getDmpListByPersonId(@PathParam("personId") String personId) {
        log.info("Return dmp list for user id=" + personId);
        return dmpService.getDmpListByPersonId(personId);
    }


    @GET
    @Path("/dmp/{id}")
//    @RolesAllowed("user")
    public DmpDO getDmpById(@PathParam("id") String id) {
        log.info("Return dmp with id=" + id);
        return dmpService.getDmpById(Long.valueOf(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save-dmp")
    public DmpDO saveDmp(SaveDmpWrapper dmpWrapper){
        return dmpService.save(dmpWrapper);
    }
}
