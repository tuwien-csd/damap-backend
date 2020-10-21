package at.ac.tuwien.r3data;

import generated.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.re3data.schema._2_2.Re3Data;

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

    @GET
    @Path("/repository/{id}")
    @Produces(MediaType.TEXT_XML)
    Re3Data getById(@PathParam String id);

}
