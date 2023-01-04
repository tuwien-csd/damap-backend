package at.ac.tuwien.damap.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource {

    @Inject
    PersonServiceBroker personServiceBroker;

    @GET
    @Path("/{id}")
    public ContributorDO getPersonById(@PathParam("id") String id,
            @QueryParam("searchService") String searchServiceType) {
        log.info("Return person details for id=" + id);
        PersonService searchService = personServiceBroker.getServiceForQueryParam(searchServiceType);

        ContributorDO person = null;
        if (searchService != null) {
            person = searchService.getPersonById(id);
        }

        return person;
    }

    @GET
    @Path("/search")
    public List<ContributorDO> getPersonSearchResult(@QueryParam("q") String searchTerm,
            @QueryParam("searchService") String searchServiceType) {

        log.info("Return person list for query=" + searchTerm + "&searchService=" + searchServiceType);

        PersonService searchService = personServiceBroker.getServiceForQueryParam(searchServiceType);

        List<ContributorDO> persons = List.of();
        if (searchService != null) {
            persons = searchService.getPersonSearchResult(searchTerm);
        }

        return persons;
    }


}
