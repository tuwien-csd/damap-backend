package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
@RegisterRestClient(configKey = "rest.persons")
@Produces(MediaType.APPLICATION_JSON)
public interface MockPersonRestService {

    @GET
    @Path("/persons")
    List<ContributorDO> getContributorDetails(@QueryParam("universityId") String uniId);

    @GET
    @Path("/persons")
    List<ContributorDO> getContributorSearchResult();
}
