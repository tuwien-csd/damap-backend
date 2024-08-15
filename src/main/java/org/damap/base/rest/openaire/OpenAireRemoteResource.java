package org.damap.base.rest.openaire;

import generated.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/** OpenAireRemoteResource interface. */
@RegisterRestClient(configKey = "rest.openaire")
@Produces(MediaType.APPLICATION_XML)
public interface OpenAireRemoteResource {

  /**
   * search.
   *
   * @param doi a {@link java.lang.String} object
   * @return a {@link generated.Response} object
   */
  @GET
  @Path("/datasets")
  Response search(@QueryParam("doi") String doi);
}
