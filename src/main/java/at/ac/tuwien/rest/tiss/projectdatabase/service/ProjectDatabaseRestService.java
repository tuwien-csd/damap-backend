package at.ac.tuwien.rest.tiss.projectdatabase.service;

import at.ac.tuwien.rest.tiss.projectdatabase.dto.ProjectDetailsWrapper;
import at.ac.tuwien.rest.tiss.projectdatabase.dto.ProjectOverviewList;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;

@Path("/rest")
@RegisterRestClient
public interface ProjectDatabaseRestService {


    @GET
    @Path("/project/v3/{projectId}")
    @Produces("application/json")
    ProjectDetailsWrapper getProjectDetails(@PathParam String projectId);


    @GET
    @Path("/projectsearch/org-criteria/v2")
    @Produces("application/json")
    ProjectOverviewList getProjectsByOrgCriteria(@QueryParam("instituteTid") String instituteId, @QueryParam("projectleaderTid") String projectleaderId);

}
