package at.ac.tuwien.dmap.rest;

import at.ac.tuwien.dmap.repo.DataManagementPlanRepo;
import at.ac.tuwien.dmap.rest.domain.DataManagementPlanDto;
import at.ac.tuwien.dmap.rest.domain.Mapper;

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

    private Mapper mapper = new Mapper();

    @GET
    @Path("/all")
    @RolesAllowed("user")
    public List<DataManagementPlanDto> getAll() {
        return mapper.mapDataManagementPlanToDataManagementPlanDto(dataManagementPlanRepo.getAll());
    }
}
