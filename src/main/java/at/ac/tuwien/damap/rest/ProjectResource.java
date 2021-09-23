package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectMemberDO;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/projects")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class ProjectResource {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    ProjectService projectService;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @GET
    @RolesAllowed("user")
    public List<ProjectDO> getProjectList() {
        log.info("Get project suggestions");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        return projectService.getProjectList(personId);
    }

    /* TODO: Strategy for permission check required for restricted projects */
    @GET
    @Path("/{id}/staff")
    public List<ProjectMemberDO> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectService.getProjectStaff(projectId);
    }

    private String getPersonId() {
        if (jsonWebToken.claim(authUser).isEmpty()) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return String.valueOf(jsonWebToken.claim(authUser).get());
    }
}
