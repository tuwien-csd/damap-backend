package at.ac.tuwien.damap.r3data;

import at.ac.tuwien.damap.r3data.dto.RepositoryDetails;
import at.ac.tuwien.damap.r3data.mapper.RepositoryMapper;
import generated.Repository;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@Path("/api/repositories")
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
    @Path("/recommended")
    public List<RepositoryDetails> getRecommended() {
        log.info("Get recommended repositories");
        return repositoriesService.getRecommended();
    }

    @GET
    @Path("/{id}")
    public RepositoryDetails getById(@PathParam String id) {
        log.info("Get repository with id: " + id);
        return RepositoryMapper.mapToRepositoryDetails(repositoriesService.getById(id), id);
    }

    @GET
    @Path("/search")
    public List<Repository> search(@Context UriInfo uriInfo) {
        log.info("Search repositories: " + uriInfo.getQueryParameters());
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
        return repositoriesService.search(params);
    }

}
