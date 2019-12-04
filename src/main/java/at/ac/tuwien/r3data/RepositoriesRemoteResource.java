package at.ac.tuwien.r3data;

import generated.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1")
@RegisterRestClient
public interface RepositoriesRemoteResource {

    @GET
    @Path("/repositories")
    @Produces(MediaType.TEXT_XML)
    List<Repository> getAll();

}
