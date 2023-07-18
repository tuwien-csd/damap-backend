package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

public interface ResourceCreate<Entity, Schema> {

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    Entity create(Schema data);
}
