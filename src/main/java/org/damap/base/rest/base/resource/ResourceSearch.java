package org.damap.base.rest.base.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.damap.base.rest.base.ResultList;

/** ResourceSearch interface. */
public interface ResourceSearch<E> {

  /**
   * search.
   *
   * @param uriInfo a {@link jakarta.ws.rs.core.UriInfo} object
   * @return a {@link org.damap.base.rest.base.ResultList} object
   */
  @GET
  @Path("")
  ResultList<E> search(@Context UriInfo uriInfo);
}
