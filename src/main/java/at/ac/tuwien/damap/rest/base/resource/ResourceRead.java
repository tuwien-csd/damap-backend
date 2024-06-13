package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

public interface ResourceRead<E> {

    @GET
    @Path("/{id}")
    E read(@PathParam("id") String id, @Context UriInfo uriInfo);
}
