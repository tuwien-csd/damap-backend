package at.ac.tuwien.damap.rest;

import at.ac.tuwien.rest.madmp.dto.MaDmp;
import at.ac.tuwien.rest.madmp.service.MaDmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/madmp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaDmpResource {
    private static final Logger log = LoggerFactory.getLogger(MaDmpResource.class);

    @Inject
    MaDmpService maDmpService;

    @GET
    @Path("/{id}")
    public MaDmp getById(@PathParam("id") String id) {
        log.info("Return maDMP for DMP with id=" + id);
        return maDmpService.getById(id);
    }
}
