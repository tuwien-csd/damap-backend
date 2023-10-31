package at.ac.tuwien.damap.rest.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;

public interface ResourceSearch<E> {

    @GET
    @Path("")
    ResultList<E> search(@Context UriInfo uriInfo);
}
