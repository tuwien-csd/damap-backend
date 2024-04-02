package at.ac.tuwien.damap.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import at.ac.tuwien.damap.rest.config.domain.ConfigDO;
import at.ac.tuwien.damap.rest.config.domain.PersonServiceConfigurations;
import lombok.extern.jbosslog.JBossLog;

import java.net.URL;
import java.util.Optional;

@Path("/api/config")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@JBossLog
public class ConfigResource {
    @ConfigProperty(name = "damap.auth.frontend.url")
    String authUrl;

    @ConfigProperty(name = "damap.auth.frontend.client")
    String authClient;

    @ConfigProperty(name = "damap.auth.scope")
    String authScope;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @ConfigProperty(name = "damap.env")
    String env;

    @ConfigProperty(name = "damap.person-services")
    PersonServiceConfigurations personServiceConfigurations;

    @ConfigProperty(name = "damap.fits-url")
    Optional<URL> fitsUrl;

    @GET
    public ConfigDO config() {
        ConfigDO configDO = new ConfigDO();
        configDO.setAuthUrl(authUrl);
        configDO.setAuthClient(authClient);
        configDO.setAuthScope(authScope);
        configDO.setAuthUser(authUser);
        configDO.setEnv(env);
        configDO.setPersonSearchServiceConfigs(personServiceConfigurations.getConfigs());
        configDO.setFitsServiceAvailable(getFitsServiceAvailability());

        return configDO;
    }

    private boolean getFitsServiceAvailability() {
        return fitsUrl.isPresent();
    }
}
