package org.damap.base.rest.invenio_damap;

import io.quarkus.resteasy.reactive.server.EndpointDisabled;
import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.madmp.dto.Dataset;
import org.damap.base.security.SecurityService;
import org.damap.base.validation.AccessValidator;

@Path("/api/madmps")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
@EndpointDisabled(name = "invenio.disabled", stringValue = "true")
public class InvenioDAMAPResource {

  @Inject
  public InvenioDAMAPResource(
      AccessValidator accessValidator,
      InvenioDAMAPService invenioDAMAPService,
      SecurityService securityService) {
    this.accessValidator = accessValidator;
    this.invenioDAMAPService = invenioDAMAPService;
    this.securityService = securityService;
  }

  AccessValidator accessValidator;
  InvenioDAMAPService invenioDAMAPService;
  SecurityService securityService;

  @GET
  public List<DmpDO> getDmpListByPerson(@Context HttpHeaders headers) {
    SimpleEntry<List<DmpDO>, String> result =
        invenioDAMAPService.resolveDmpsAndIds(securityService.checkIfUserIsAuthorized(headers));
    return result.getKey();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public DmpDO addDataSetToDmp(@Context HttpHeaders headers, DMPPayload payload) {
    SimpleEntry<List<DmpDO>, String> result =
        invenioDAMAPService.resolveDmpsAndIds(securityService.checkIfUserIsAuthorized(headers));

    long dmpId = payload.getDmpId();
    log.info("Add dataset to dmp with id: " + dmpId);
    Dataset dataset = payload.getDataset();

    if (result.getKey().stream().noneMatch(dmpDO -> dmpDO.getId().equals(dmpId))) {
      throw new NotFoundException("DMP with id " + dmpId + " could not be found.");
    }

    String personId = result.getValue();
    // Assuming all IDs provided fetch the same DMPs, check permissions.
    if (!accessValidator.canEditDmp(dmpId, personId))
      throw new ForbiddenException(
          "Person " + personId + "Not authorized to access dmp with id " + dmpId);
    return invenioDAMAPService.addDataSetToDMP(dmpId, dataset);
  }
}
