package at.ac.tuwien.damap.rest.openaire;

import generated.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "rest.openaire")
@Produces(MediaType.TEXT_XML)
public interface OpenAireRemoteResource {

    @GET
    @Path("/datasets")
    Response search(@QueryParam("doi") String doi);

}
