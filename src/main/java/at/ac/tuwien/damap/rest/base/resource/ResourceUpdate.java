package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

public interface ResourceUpdate<E, S> {

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    E update(@PathParam("id") String id, S data);
}
