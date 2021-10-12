package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.madmp.dto.MaDmp;
import at.ac.tuwien.damap.rest.madmp.service.MaDmpService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/madmp")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class MaDmpResource {

    @Inject
    SecurityService securityService;

    @Inject
    AccessValidator accessValidator;

    @Inject
    MaDmpService maDmpService;

    @Inject
    DmpService dmpService;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @GET
    @Path("/{id}")
    public MaDmp getById(@PathParam("id") long id) {
        log.info("Return maDMP for DMP with id: " + id);
        String personId = this.getPersonId();
        if(!accessValidator.canViewDmp(id, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + id);
        }
        return maDmpService.getById(id);
    }

    @GET
    @Path("/file/{id}")
    public Response getFileById(@PathParam("id") long id) {
        log.info("Return maDMP file for DMP with id: " + id);
        String personId = this.getPersonId();
        if(!accessValidator.canViewDmp(id, personId)){
            throw new ForbiddenException("Not authorized to access dmp with id " + id);
        }

        String filename = dmpService.getDefaultFileName(id);

        Response.ResponseBuilder response = Response.ok((Object) maDmpService.getById(id));
        response.header("Content-Disposition", "attachment; filename=" + filename + ".json")
                .header("Access-Control-Expose-Headers","Content-Disposition");
        return response.build();
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }
}
