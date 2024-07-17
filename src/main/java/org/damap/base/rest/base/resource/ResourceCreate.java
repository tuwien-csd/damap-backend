package org.damap.base.rest.base.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/** ResourceCreate interface. */
public interface ResourceCreate<E, S> {

  /**
   * create.
   *
   * @param data a S object
   * @return a E object
   */
  @POST
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  E create(S data);
}
