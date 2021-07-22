package at.ac.tuwien.r3data;

import generated.Repository;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.re3data.schema._2_2.Re3Data;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@JBossLog
@Path("/repositories")
@RolesAllowed("user")
@Produces(MediaType.APPLICATION_JSON)
public class RepositoriesResource {

    @Inject
    RepositoriesService repositoriesService;

    @GET
    public List<Repository> getAll() {
        log.info("Get all repositories");
        return repositoriesService.getAll();
    }

    @GET
    @Path("/{id}")
    public Re3Data getById(@PathParam String id) {
        log.info("Get repository with id: " + id);
        return repositoriesService.getById(id);
    }

    @GET
    @Path("/search")
    public List<Repository> search(@Context UriInfo uriInfo) {
        log.info("Search repositories: " + uriInfo.getQueryParameters());
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
        return repositoriesService.search(params);
    }

}
