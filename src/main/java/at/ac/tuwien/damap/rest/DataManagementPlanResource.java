package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.repo.DataManagementPlanRepo;
import at.ac.tuwien.damap.rest.domain.DataManagementPlanDto;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plans")
@Produces(MediaType.APPLICATION_JSON)
public class DataManagementPlanResource {

    @Inject DataManagementPlanRepo dataManagementPlanRepo;

    @GET
    @Path("/all")
    @RolesAllowed("user")
    public List<DataManagementPlanDto> getAll() {
//        todo mapDataManagementPlanToDataManagementPlanDto(dataManagementPlanRepo.getAll());
        return null;
    }
}
