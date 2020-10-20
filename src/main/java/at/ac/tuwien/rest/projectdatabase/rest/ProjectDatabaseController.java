package at.ac.tuwien.rest.projectdatabase.rest;

import at.ac.tuwien.rest.projectdatabase.dto.*;
import at.ac.tuwien.rest.projectdatabase.service.ProjectDatabaseRestService;
import at.ac.tuwien.rest.projectdatabase.dto.*;
import at.ac.tuwien.rest.projectdatabase.service.ProjectDatabaseServiceImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/pdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectDatabaseController {
    private static final Logger log = LoggerFactory.getLogger(ProjectDatabaseController.class);

    @Inject
    @RestClient
    private ProjectDatabaseRestService projectDatabaseRestService;

    @Inject
    private ProjectDatabaseServiceImpl projectDatabaseService;

    @GET
    @Path("/project/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectDetails getProjectDetails(
            @PathParam("id") String projectId
    ) {
        log.info(String.format("Get Project Details for Project ID=%s", projectId));

        ProjectDetailsWrapper projectDetailsWrapper = projectDatabaseRestService.getProjectDetails(projectId);
        return projectDetailsWrapper.getProject();
    }

    @GET
    @Path("/search-projects/{instituteId}/{projectleaderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectOverview> getProjectsByOrgCriteria(
            @PathParam("instituteId") String instituteId,
            @PathParam("projectleaderId") String projectleaderId
    ) {
        log.info(String.format("Get projects for instituteId=%s and projectLeaderId=%s", instituteId, projectleaderId));
        return projectDatabaseRestService.getProjectsByOrgCriteria(instituteId, projectleaderId).getProject();
    }

    @GET
    @Path("/search-projects/{instituteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectOverviewList getProjectsByOrgCriteria(
            @PathParam("instituteId") String instituteId
    ) {
        log.info(String.format("Get projects for instituteId=%s", instituteId));
        return projectDatabaseRestService.getProjectsByOrgCriteria(instituteId, null);
    }


    @GET
    @Path("/search-projectsByPl/{projectLeaderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectOverviewList getProjectsByPersonIdCriteria(
            @PathParam("projectLeaderId") String projectLeaderId
    ) {
        log.info(String.format("Get projects for projectLeaderId=%s", projectLeaderId));
        return projectDatabaseRestService.getProjectsByOrgCriteria(null, projectLeaderId);
    }


    @GET
    @Path("/suggest-projects/{personId}")
    public List<ProjectOverview> getProjectSuggestionsForPerson(@PathParam("personId") String personId) {
        log.info(String.format("Get project suggestions for person with id: %s", personId));
        return projectDatabaseService.getProjectSuggestionsForPerson(personId);
    }


    @GET
    @Path("/project/{id}/staff")
    public List<ProjectMemberDetails> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectDatabaseService.getProjectStaff(projectId);
    }
}
