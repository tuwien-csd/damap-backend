package at.ac.tuwien.damap.r3data;

import generated.Repository;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.re3data.schema._2_2.Re3Data;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/repositories")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
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
