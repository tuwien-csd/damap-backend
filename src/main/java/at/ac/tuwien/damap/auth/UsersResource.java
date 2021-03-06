package at.ac.tuwien.damap.auth;

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/users")
@Produces(MediaType.TEXT_PLAIN)
public class UsersResource {

    @Inject SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    @NoCache
    public String me() {
        return securityIdentity.getPrincipal().getName();
    }

}
