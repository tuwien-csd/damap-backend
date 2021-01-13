package at.ac.tuwien.rest.projectdatabase.service;

import at.ac.tuwien.rest.projectdatabase.dto.ProjectDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/pdb/rest")
@RegisterRestClient
public interface ProjectDatabaseRestService {

    @GET
    @Path("/restricted/damap/project/{projectId}")
    @Produces("application/json")
    ProjectDTO getProjectDetails(@PathParam String projectId);

    @GET
    @Path("/restricted/damap/projectsByCriteria")
    @Produces("application/json")
    List<ProjectDTO> getProjectsByCriteria(@QueryParam("orgUnitId") String orgUnitId, @QueryParam("projectleaderId") String projectleaderId);
}
