package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

public interface ResourceUpdate<Entity, Schema> {

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Entity update(@PathParam("id") String id, Schema data);
}
