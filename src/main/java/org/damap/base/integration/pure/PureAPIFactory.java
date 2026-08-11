package org.damap.base.integration.pure;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.damap.base.rest.config.domain.TenantConfigResolver;
import org.damap.base.security.SecurityService;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 * Factory responsible for producing the appropriate {@link PureAPI} implementation based on the
 * tenant-specific configuration.
 */
@ApplicationScoped
class PureAPIFactory {
  @Inject TenantConfigResolver tenantConfigResolver;

  @Inject SecurityService securityService;

  @Inject Instance<FileBasedPureAPI> fileAPI;

  private final Map<String, HTTPBasedPureAPI> httpClients = new ConcurrentHashMap<>();

  /**
   * Creates a {@link PureAPI} implementation according to the configured backend type. Allowed
   * values are file and http.
   *
   * @return the configured {@link PureAPI} implementation
   * @throws IllegalArgumentException if the configured backend is not supported
   */
  @Produces
  @RequestScoped
  @Priority(1)
  PureAPI create() {
    String backend = tenantConfigResolver.getTenantAwareConfig().elsevierPureBackend();
    return switch (backend) {
      case "file" -> fileAPI.get();
      case "http" -> getClient();
      default -> throw new IllegalArgumentException("Pure API backend not supported: " + backend);
    };
  }

  /**
   * Returns a cached HTTP-based Pure API client for the current tenant.
   *
   * <p>If no client exists for the resolved tenant key, a new REST client is created using the
   * configured Pure API endpoint and registered authentication provider.
   *
   * <p>The client needs to be created dynamically for multitenancy, since different clients need to
   * be chosen at runtime, depending on which tenant tries to use the PureAPI.
   *
   * @return a tenant-specific {@link HTTPBasedPureAPI} client
   */
  private HTTPBasedPureAPI getClient() {
    String aff = securityService.getAffiliation();
    if (aff == null || tenantConfigResolver.isMultitenancyDisabled()) {
      aff = "no-tenant-registered";
    }
    return httpClients.computeIfAbsent(
        aff,
        missingClient ->
            RestClientBuilder.newBuilder()
                .baseUri(
                    URI.create(
                        tenantConfigResolver.getTenantAwareConfig().elsevierPureEndpointUrl()))
                .register(PureAuthenticationHeaderFactory.class)
                .build(HTTPBasedPureAPI.class));
  }
}
