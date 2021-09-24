package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.config.domain.ConfigDO;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class ConfigResource {

    @ConfigProperty(name = "damap.auth.url")
    String authUrl;

    @ConfigProperty(name = "damap.auth.client.frontend")
    String authClient;

    @ConfigProperty(name = "damap.auth.scope")
    String authScope;

    @ConfigProperty(name = "damap.auth.user")
    String authUser;

    @GET
    public ConfigDO config() {
        ConfigDO configDO = new ConfigDO();
        configDO.setAuthUrl(authUrl);
        configDO.setAuthClient(authClient);
        configDO.setAuthScope(authScope);
        configDO.setAuthUser(authUser);
        return configDO;
    }
}
