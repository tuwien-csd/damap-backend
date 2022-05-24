package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.openaire.service.OpenAireService;
import generated.Response;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/openaire")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class OpenAireResource {

    @Inject
    OpenAireService openAireService;

    @GET
    public Response search(@QueryParam String doi) {
        log.info("Search for dataset with DOI: " + doi);
        return openAireService.search(doi);
    }
}
