package at.ac.tuwien.damap.rest.persons;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

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
