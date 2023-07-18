package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("")
@RegisterRestClient(configKey = "rest.projects")
@Produces(MediaType.APPLICATION_JSON)
public interface MockProjectRestService {

    @GET
    @Path("/projects")
    List<ProjectDO> getProjectDetails(@QueryParam("universityId") String uniId);

    @GET
    @Path("/projects")
    List<ProjectDO> getProjectList(@QueryParam("q") String query);

    @GET
    @Path("/projects")
    List<ProjectDO> getRecommended(@QueryParam("description_like") @DefaultValue("recommend") String description);

    @GET
    @Path("/project-supplement")
    ProjectSupplementDO getProjectSupplement();
}
