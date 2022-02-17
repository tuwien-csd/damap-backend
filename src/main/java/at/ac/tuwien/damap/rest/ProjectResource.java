package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import at.ac.tuwien.damap.security.SecurityService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/projects")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class ProjectResource {

    @Inject
    SecurityService securityService;

    @Inject
    ProjectService projectService;

    @Inject
    DmpService dmpService;

    @GET
    public List<ProjectDO> getProjectList() {
        log.info("Get project suggestions");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        List<ProjectDO> projectDOList = projectService.getProjectList(personId);
        return dmpService.checkExistingDmps(projectDOList);
    }

    /* TODO: Strategy for permission check required for restricted projects */
    @GET
    @Path("/{id}/staff")
    public List<ContributorDO> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectService.getProjectStaff(projectId);
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }
}
