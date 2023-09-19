package at.ac.tuwien.damap.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.resource.ResourceSearch;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/projects")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class ProjectResource implements ResourceSearch<ProjectDO> {

    @Inject
    ProjectService projectService;

    @Inject
    DmpService dmpService;

    /* TODO: Strategy for permission check required for restricted projects */
    @GET
    @Path("/{id}/staff")
    public List<ContributorDO> getProjectMembers(@PathParam("id") String projectId) {
        log.info(String.format("Get Project Staff for Project ID=%s", projectId));
        return projectService.getProjectStaff(projectId);
    }

    @Override
    public ResultList<ProjectDO> search(UriInfo uriInfo) {
        var queryParams = uriInfo.getQueryParameters();
        log.info("Return projects for query=" + queryParams.toString());

        var resultList = projectService.search(queryParams);
        resultList.setItems(dmpService.checkExistingDmps(resultList.getItems()));

        return resultList;
    }

    @GET
    @Path("/recommended")
    public ResultList<ProjectDO> recommended(@Context UriInfo uriInfo) {
        var queryParams = uriInfo.getQueryParameters();
        log.info("Return recommended projects for query=" + queryParams.toString());

        var resultList = projectService.getRecommended(queryParams);
        resultList.setItems(dmpService.checkExistingDmps(resultList.getItems()));

        return resultList;
    }
}
