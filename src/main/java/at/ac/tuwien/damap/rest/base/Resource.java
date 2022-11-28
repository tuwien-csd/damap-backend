package at.ac.tuwien.damap.rest.base;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public abstract class Resource<Entity, Schema> {

    Service<Entity, Schema> service;

    @GET
    @Path("/search")
    public ResultList<Entity> search(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
        log.info("Search " + this.getClass().getSimpleName() + ":" + uriInfo.getQueryParameters());

        return service.search(params);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Entity create(Schema data) {
        log.info("POST " + this.getClass().getSimpleName());
        return service.create(data);
    }

    @GET
    @Path("/{id}")
    public Entity read(@PathParam("id") String id) {
        log.info("GET " + this.getClass().getSimpleName() + ": " + id);
        return service.read(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Entity update(@PathParam("id") String id, Schema data) {
        log.info("PUT " + this.getClass().getSimpleName() + ": " + id);
        return service.update(id, data);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        log.info("DELETE " + this.getClass().getSimpleName() + ": " + id);
        service.delete(id);
    }

}
