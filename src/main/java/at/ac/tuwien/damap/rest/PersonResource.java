package at.ac.tuwien.damap.rest;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.damap.enums.ESearchServiceType;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.MockUniversityPersonServiceImpl;
import at.ac.tuwien.damap.rest.persons.ORCIDPersonServiceImpl;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

/* Resource currently unused, but will be required for person search at a later stage */
// TODO: remove unused functions
@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource {

    @Inject
    MockUniversityPersonServiceImpl personService;

    @Inject
    @Dependent
    ORCIDPersonServiceImpl orcidPersonService;

    @GET
    @Path("/{id}")
    public ContributorDO getPersonById(@PathParam("id") String id) {
        log.info("Return person details for id=" + id);
        return personService.getPersonById(id);
    }

    @GET
    @Path("/search")
    public List<ContributorDO> getPersonSearchResult(@QueryParam("q") String searchTerm,
            @QueryParam("searchService") ESearchServiceType searchServiceType) {
        log.info("Return person list for query=" + searchTerm);

        searchServiceType = searchServiceType == null ? ESearchServiceType.ORCID : searchServiceType;
        PersonService searchService;

        switch (searchServiceType) {
            case ORCID:
                searchService = orcidPersonService;
                break;
            case UNIVERSITY:
            default:
                searchService = personService;
                break;
        }

        List<ContributorDO> persons = searchService.getPersonSearchResult(searchTerm);
        return persons;
    }
}
