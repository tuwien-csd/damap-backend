package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.ProjectDO;
import at.ac.tuwien.damap.rest.domain.ProjectMemberDO;
import at.ac.tuwien.rest.projectdatabase.service.ProjectDatabaseService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;
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
    ProjectDatabaseService projectDatabaseService;

    @GET
    @Path("/suggest")
    @RolesAllowed("user")
    public List<ProjectDO> getProjectSuggestionsForPerson() {
        log.info("Get project suggestions");
        String personId = this.getPersonId();
        log.info("User id: " + personId);
        return projectDatabaseService.getProjectSuggestionsForPerson(personId);
    }

    /* TODO: Strategy for permission check required for restricted projects */
    @GET
    @Path("/{id}/staff")
    public List<ProjectMemberDO> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectDatabaseService.getProjectStaff(projectId);
    }

    private String getPersonId() {
        if (jsonWebToken.claim("tissID").isEmpty()) {
            throw new AuthenticationFailedException("Tiss ID is missing.");
        }
        return String.valueOf(jsonWebToken.claim("tissID").get());
    }
}
