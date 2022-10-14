package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
@RegisterRestClient(configKey = "rest.projects")
@Produces(MediaType.APPLICATION_JSON)
public interface MockProjectRestService {

    @GET
    @Path("/projects")
    List<ProjectDO>  getProjectDetails(@QueryParam("universityId") String uniId);

    @GET
    @Path("/projects")
    List<ProjectDO> getProjectList();

    @GET
    @Path("/project-supplement")
    ProjectSupplementDO getProjectSupplement();
}
