package at.ac.tuwien.damap.rest.persons.orcid;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest.orcid.search")
@Path("/v3.0")
@Produces(MediaType.APPLICATION_JSON)
public interface OrcidPersonService {

    @Path("/expanded-search")
    @GET
    @ClientHeaderParam(name = "accept", value = MediaType.APPLICATION_JSON)
    ORCIDExpandedSearchResult getAll(@QueryParam("q") String query, @QueryParam("rows") int rows);

    @Path("/{orcid}/person")
    @GET
    @ClientHeaderParam(name = "accept", value = MediaType.APPLICATION_JSON)
    ORCIDPerson get(@PathParam(value = "orcid") String orcid);
}
