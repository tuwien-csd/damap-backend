package at.ac.tuwien.damap.rest.invenio_damap;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.madmp.dto.Dataset;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/invenio-damap")
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class InvenioDAMAPResource {

    @Inject
    public InvenioDAMAPResource(SecurityService securityService, AccessValidator accessValidator, AccessRepo accessRepo,
            DmpService dmpService, InvenioDAMAPService invenioDAMAPService) {
        this.securityService = securityService;
        this.accessValidator = accessValidator;
        this.accessRepo = accessRepo;
        this.dmpService = dmpService;
        this.invenioDAMAPService = invenioDAMAPService;
    }

    SecurityService securityService;
    AccessValidator accessValidator;
    AccessRepo accessRepo;
    DmpService dmpService;
    InvenioDAMAPService invenioDAMAPService;

    @ConfigProperty(name = "invenio.shared-secret")
    String sharedSecret;

    /*
     * Maybe make it configurable?
     * Could be more elaborate but should suffice for PoC
     * Could also go into the {@link at.ac.tuwien.damap.security.SecurityService},
     * but keeping everything together for PoC
     *
     */
    private boolean validateAuthHeader(HttpHeaders headers) {
        // return sharedSecret.equals(headers.getHeaderString("Authorization"));
        String authHeader = headers.getHeaderString("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer "

            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                JWSVerifier verifier = new MACVerifier(sharedSecret);

                // Verify the signature using the verifier
                System.out.println(signedJWT.verify(verifier));
                if (signedJWT.verify(verifier)) {
                    // String userEmail = signedJWT.getJWTClaimsSet().getStringClaim("email");
                    return true; // Token is valid
                }
                return false;
                // return sharedSecret.equals(jwtClaims.getStringClaim("sub"));
            } catch (ParseException | JOSEException e) {
                return false;
            }
        }
        // No Authorization header or not in the correct format
        return false;
    }

    private void checkIfUserIsAuthorized(String personId, HttpHeaders headers) {
        // This could allow us to authenticate users with OIDC, and allow admins to
        // access all DMPs.
        if (!(securityService.isAdmin() || Objects.equals(securityService.getUserId(), personId))) {
            // Since this will always evaluate to false as of right now, it will check the
            // auth header
            if (!validateAuthHeader(headers)) {
                throw new ForbiddenException();
            }
        }
    }

    @GET
    @Path("/person/{personId}")
    public List<DmpDO> getDmpListByPerson(@PathParam String personId, @Context HttpHeaders headers) {
        log.info("Return dmps for person id: " + personId);

        checkIfUserIsAuthorized(personId, headers);
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        return accessList.stream().map(access -> dmpService.getDmpById(access.getDmp().id)).toList();
    }

    @POST
    @Path("/{dmpId}/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public DmpDO addDataSetToDmp(@PathParam long dmpId, @PathParam String personId, Dataset dataset,
            @Context HttpHeaders headers) {
        log.info("Add dataset to dmp with id: " + dmpId);

        checkIfUserIsAuthorized(personId, headers);
        if (!accessValidator.canEditDmp(dmpId, personId)) {
            throw new ForbiddenException("Not authorized to access dmp with id " + dmpId);
        }

        return invenioDAMAPService.addDataSetToDMP(dmpId, dataset);
    }
}
