package at.ac.tuwien.damap.rest.persons;

import java.net.URL;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import io.quarkus.restclient.config.RestClientsConfig;

@Path("")
// needed for inject annotations
@RegisterRestClient(configKey = MockPersonRestService.configKey)
@Produces(MediaType.APPLICATION_JSON)
public interface MockPersonRestService {

    // used for dynamic creation of service
    String configKey = "rest.persons";

    @GET
    @Path("/persons")
    List<ContributorDO> getContributorDetails(@QueryParam("universityId") String uniId);

    @GET
    @Path("/persons")
    List<ContributorDO> getContributorSearchResult();

    static MockPersonRestService create()
            throws Exception {
        var cconfig = RestClientsConfig.getInstance().getClientConfig(configKey);

        String u = cconfig.url.get();
        return RestClientBuilder.newBuilder()
                .baseUrl(new URL(u))
                .build(MockPersonRestService.class);
    }
}
