package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ResourceSearch<Entity> {

    @GET
    @Path("/search")
    ResultList<Entity> search(@Context UriInfo uriInfo);
}
