package at.ac.tuwien.damap.rest.projectdatabase.service;

import at.ac.tuwien.damap.rest.projectdatabase.dto.ProjectDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/pdb/rest")
@RegisterRestClient(configKey = "damap.projects")
@Produces(MediaType.APPLICATION_JSON)
public interface ProjectDatabaseRestService {

    @GET
    @Path("/restricted/damap/project/{projectId}")
    ProjectDTO getProjectDetails(@PathParam String projectId);

    @GET
    @Path("/restricted/damap/projectsByCriteria")
    List<ProjectDTO> getProjectsByCriteria(@QueryParam("orgUnitId") String orgUnitId, @QueryParam("projectleaderId") String projectleaderId);
}
