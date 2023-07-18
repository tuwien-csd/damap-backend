package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

public interface ResourceUpdate<Entity, Schema> {

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Entity update(@PathParam("id") String id, Schema data);
}
