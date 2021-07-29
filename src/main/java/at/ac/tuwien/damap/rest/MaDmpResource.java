package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.service.DmpService;
import at.ac.tuwien.rest.madmp.dto.MaDmp;
import at.ac.tuwien.rest.madmp.service.MaDmpService;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/madmp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class MaDmpResource {

    @Inject
    MaDmpService maDmpService;

    @Inject
    DmpService dmpService;

    @GET
    @Path("/{id}")
    public MaDmp getById(@PathParam("id") long id) {
        log.info("Return maDMP for DMP with id=" + id);
        return maDmpService.getById(id);
    }

    @GET
    @Path("/file/{id}")
    public Response getFileById(@PathParam("id") long id) {
        log.info("Return maDMP file for DMP with id=" + id);

        String filename = dmpService.getDefaultFileName(id);

        Response.ResponseBuilder response = Response.ok((Object) maDmpService.getById(id));
        response.header("Content-Disposition", "attachment; filename=" + filename + ".json")
                .header("Access-Control-Expose-Headers","Content-Disposition");
        return response.build();
    }
}
