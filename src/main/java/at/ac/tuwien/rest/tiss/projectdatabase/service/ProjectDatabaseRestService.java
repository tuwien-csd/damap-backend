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
    @Path("/projectsearch/v2")
    @Produces("application/json")
    ProjectOverviewList getProjectsByOrgCriteria(@QueryParam("instituteOid") String instituteOid, @QueryParam("projectleaderOid") String projectleaderOid);

}
