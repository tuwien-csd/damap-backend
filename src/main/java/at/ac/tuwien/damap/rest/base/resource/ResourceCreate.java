package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

public interface ResourceCreate<E, S> {

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    E create(S data);
}
