package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

public interface ResourceDelete {

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") String id);
}
