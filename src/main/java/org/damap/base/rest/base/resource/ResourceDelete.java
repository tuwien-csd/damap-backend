package org.damap.base.rest.base.resource;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

/** ResourceDelete interface. */
public interface ResourceDelete {

  /**
   * delete.
   *
   * @param id a {@link java.lang.String} object
   */
  @DELETE
  @Path("/{id}")
  void delete(@PathParam("id") String id);
}
