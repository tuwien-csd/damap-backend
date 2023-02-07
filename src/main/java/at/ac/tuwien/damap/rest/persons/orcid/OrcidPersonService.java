package at.ac.tuwien.damap.rest.persons.orcid;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
