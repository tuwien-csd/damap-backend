package org.damap.base.rest.base.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

public interface ResourceUpdate<E, S> {

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    E update(@PathParam("id") String id, S data);
}
