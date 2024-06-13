package at.ac.tuwien.damap.rest.base.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ResourceSearch<E> {

    @GET
    @Path("")
    ResultList<E> search(@Context UriInfo uriInfo);
}
