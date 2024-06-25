package org.damap.base.security;

import io.quarkus.arc.Unremovable;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.security.Principal;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@JBossLog
@Unremovable
// @UnlessBuildProfile("test")
@ApplicationScoped
public class SecurityService {
  @Inject SecurityIdentity securityIdentity;

  @ConfigProperty(name = "damap.auth.user")
  String authUser;

  public String getUserId() {
    final Principal principal = securityIdentity.getPrincipal();
    if (!(principal instanceof OidcJwtCallerPrincipal)) return null;

    return ((OidcJwtCallerPrincipal) principal).getClaims().getClaimValue(authUser).toString();
  }

  public String getUserName() {
    final Principal principal = securityIdentity.getPrincipal();
    if (!(principal instanceof OidcJwtCallerPrincipal)) return null;
    return ((OidcJwtCallerPrincipal) principal).getName();
  }

  public boolean isAdmin() {
    return securityIdentity.hasRole("Damap Admin");
  }
}
