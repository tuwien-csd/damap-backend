package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.service.GdprService;
import at.ac.tuwien.damap.security.SecurityService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("api/gdpr")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class GdprResource {

    @Inject
    SecurityService securityService;

    @Inject
    GdprService gdprService;

    @GET
    public List<GdprResult> read() {
        log.info("Get GDPR base data");
        return this.gdprService.getGdprBaseData(this.getPersonId());
    }

    @GET
    @Path("extended")
    public List<GdprResult> readExtended() {
        log.info("Get GDPR extended data");
        return this.gdprService.getGdprExtendedData(this.getPersonId());
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }
}
