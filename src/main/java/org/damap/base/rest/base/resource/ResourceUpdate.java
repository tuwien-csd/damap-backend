package org.damap.base.rest.base.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

/** ResourceUpdate interface. */
public interface ResourceUpdate<E, S> {

  /**
   * update.
   *
   * @param id a {@link java.lang.String} object
   * @param data a S object
   * @return a E object
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  E update(@PathParam("id") String id, @Valid S data);
}
