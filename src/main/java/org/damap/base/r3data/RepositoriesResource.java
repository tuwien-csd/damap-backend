package org.damap.base.r3data;

import generated.Repository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.r3data.dto.RepositoryDetails;
import org.damap.base.r3data.mapper.RepositoryMapper;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/api/repositories")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class RepositoriesResource {

  @Inject RepositoriesService repositoriesService;

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
