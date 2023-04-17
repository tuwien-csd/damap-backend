package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface ResourceDelete {

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") String id);
}
