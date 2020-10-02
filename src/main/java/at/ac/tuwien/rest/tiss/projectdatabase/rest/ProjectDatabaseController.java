package at.ac.tuwien.rest.tiss.projectdatabase.rest;

import at.ac.tuwien.rest.tiss.addressbook.dto.Person;
import at.ac.tuwien.rest.tiss.projectdatabase.dto.*;
import at.ac.tuwien.rest.tiss.projectdatabase.service.ProjectDatabaseRestService;
import at.ac.tuwien.rest.tiss.projectdatabase.service.ProjectDatabaseServiceImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.Form;
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
    @Path("/search-projects/{instituteOid}/{projectleaderOid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectOverview> getProjectsByOrgCriteria(
            @PathParam("instituteOid") String instituteOid,
            @PathParam("projectleaderOid") String projectleaderOid
    ) {
        log.info(String.format("Get projects for instituteOid=%s and projectLeaderOid=%s", instituteOid, projectleaderOid));
        return projectDatabaseRestService.getProjectsByOrgCriteria(instituteOid, projectleaderOid).getProject();
    }

    @GET
    @Path("/search-projects/{instituteOid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectOverviewList getProjectsByOrgCriteria(
            @PathParam("instituteOid") String instituteOid
    ) {
        log.info(String.format("Get projects for instituteOid=%s", instituteOid));
        return projectDatabaseRestService.getProjectsByOrgCriteria(instituteOid, null);
    }


    @POST
    @Path("/suggest-projects")
    public List<ProjectOverview> getProjectSuggestionsForPerson(@Form Person person) {
        log.info(String.format("Get project suggestions for person: %s", person));
        return projectDatabaseService.getProjectSuggestionsForPerson(person);
    }


    @GET
    @Path("/project/{id}/staff")
    public List<ProjectMemberDetails> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectDatabaseService.getProjectStaff(projectId);
    }
}
