package org.damap.base.rest.persons;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import org.damap.base.rest.dmp.domain.ContributorDO;

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
