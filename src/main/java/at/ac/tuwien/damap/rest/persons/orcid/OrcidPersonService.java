package at.ac.tuwien.damap.rest.persons.orcid;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest.orcid.search")
@Path("/v3.0/expanded-search")
@Produces(MediaType.APPLICATION_JSON)
public interface OrcidPersonService {

    @GET
    @ClientHeaderParam(name = "accept", value = "application/json")
    ORCIDExpandedSearchResult getAll(@QueryParam("q") String query, @QueryParam("rows") int rows);
}
