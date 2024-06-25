package org.damap.base.rest.base.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

public interface ResourceCreate<E, S> {

  @POST
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  E create(S data);
}
