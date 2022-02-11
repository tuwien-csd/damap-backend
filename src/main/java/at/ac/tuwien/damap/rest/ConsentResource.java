package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.repo.ConsentRepo;
import at.ac.tuwien.damap.rest.administration.domain.ConsentDO;
import at.ac.tuwien.damap.rest.administration.service.ConsentService;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.SaveDmpWrapper;
import at.ac.tuwien.damap.security.SecurityService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import lombok.extern.jbosslog.JBossLog;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/api/consent")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class ConsentResource {

    @Inject
    ConsentService consentService;

    @Inject
    SecurityService securityService;

    @Inject
    ConsentRepo consentRepo;

    @GET
    public ConsentDO getConsent() {
        if (consentRepo.getConsentByUniversityId(this.getPersonId()) != null) {
            return consentService.getConsentByUser(this.getPersonId()) ;
        }
        else {
            ConsentDO consent = new ConsentDO();
            String personId = this.getPersonId();
            consent.setUniversityId(personId);
            consent.setGivenDate(new Date());
            consent.setConsentGiven(false);
            return consentService.create(consent);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ConsentDO saveConsent(ConsentDO consentDO) {
        String personId = this.getPersonId();
        consentDO.setUniversityId(personId);
        consentDO.setGivenDate(new Date());
        if (consentService.getConsentByUser(this.getPersonId()) != null) {
            return consentService.update(consentDO);
        }
        else {
            return consentService.create(consentDO);
        }
    }

    private String getPersonId() {
        if (securityService == null) {
            throw new AuthenticationFailedException("User ID is missing.");
        }
        return securityService.getUserId();
    }

}
