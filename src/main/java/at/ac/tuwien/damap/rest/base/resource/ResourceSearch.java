package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ResourceSearch<Entity> {

    @GET
    @Path("")
    ResultList<Entity> search(@Context UriInfo uriInfo);
}
