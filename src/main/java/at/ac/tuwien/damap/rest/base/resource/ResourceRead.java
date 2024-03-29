package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public interface ResourceRead<E> {

    @GET
    @Path("/{id}")
    E read(@PathParam("id") String id, @Context UriInfo uriInfo);
}
