package org.damap.base.rest;

import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.gdpr.domain.GdprResult;
import org.damap.base.rest.gdpr.service.GdprService;
import org.damap.base.security.SecurityService;

/** GdprResource class. */
@Path("api/gdpr")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class GdprResource {

  @Inject SecurityService securityService;

  @Inject GdprService gdprService;

  /**
   * read.
   *
   * @return a {@link java.util.List} object
   */
  @GET
  public List<GdprResult> read() {
    log.info("Get GDPR base data");
    return this.gdprService.getGdprBaseData(this.getPersonId());
  }

  /**
   * readExtended.
   *
   * @return a {@link java.util.List} object
   */
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
