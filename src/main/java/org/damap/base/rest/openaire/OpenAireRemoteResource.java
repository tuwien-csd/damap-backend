package org.damap.base.rest.openaire;

import generated.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@RegisterRestClient(configKey = "rest.openaire")
@Produces(MediaType.APPLICATION_XML)
public interface OpenAireRemoteResource {

  @GET
  @Path("/datasets")
  Response search(@QueryParam("doi") String doi);
}
