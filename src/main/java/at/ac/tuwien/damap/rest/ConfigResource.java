package at.ac.tuwien.damap.rest;

import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> config() {
        log.info("Get config");
        Map<String, String> config = new HashMap<>();
        config.put("authUrl", authUrl);
        config.put("authClient", authClient);
        config.put("authScope", authScope);
        config.put("authUser", authUser);
        return config;
    }
}
