package at.ac.tuwien.damap.security;

import io.quarkus.arc.Unremovable;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.Principal;

@JBossLog
@Unremovable
@UnlessBuildProfile("test")
@ApplicationScoped
public class SecurityService {
    @Inject
    SecurityIdentity securityIdentity;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    public String getUserId() {
        final Principal principal = securityIdentity.getPrincipal();
        if (!(principal instanceof OidcJwtCallerPrincipal))
            return null;

        return ((OidcJwtCallerPrincipal) principal).getClaims().getClaimValue(authUser).toString();
    }

    public String getUserName() {
        final Principal principal = securityIdentity.getPrincipal();
        if (!(principal instanceof OidcJwtCallerPrincipal))
            return null;
        return ((OidcJwtCallerPrincipal) principal).getName();
    }
}
