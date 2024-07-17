package org.damap.base.security;

import io.quarkus.arc.Unremovable;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.security.Principal;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/** SecurityService class. */
@JBossLog
@Unremovable
// @UnlessBuildProfile("test")
@ApplicationScoped
public class SecurityService {
  @Inject SecurityIdentity securityIdentity;

  @ConfigProperty(name = "damap.auth.user")
  String authUser;

  /**
   * getUserId.
   *
   * @return a {@link java.lang.String} object
   */
  public String getUserId() {
    final Principal principal = securityIdentity.getPrincipal();
    if (!(principal instanceof OidcJwtCallerPrincipal)) return null;

    return ((OidcJwtCallerPrincipal) principal).getClaims().getClaimValue(authUser).toString();
  }

  /**
   * getUserName.
   *
   * @return a {@link java.lang.String} object
   */
  public String getUserName() {
    final Principal principal = securityIdentity.getPrincipal();
    if (!(principal instanceof OidcJwtCallerPrincipal)) return null;
    return ((OidcJwtCallerPrincipal) principal).getName();
  }

  /**
   * isAdmin.
   *
   * @return a boolean
   */
  public boolean isAdmin() {
    return securityIdentity.hasRole("Damap Admin");
  }
}
