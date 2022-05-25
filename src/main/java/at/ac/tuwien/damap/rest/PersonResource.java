package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/* Resource currently unused, but will be required for person search at a later stage */
// TODO: remove unused functions
@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    @Path("/{id}")
    public ContributorDO getPersonById(@PathParam("id") String id) {
        log.info("Return person details for id=" + id);
        return personService.getPersonById(id);
    }

    @GET
    @Path("/search")
    public List<ContributorDO> getPersonSearchResult(@QueryParam("q") String searchTerm) {
        log.info("Return person list for query=" + searchTerm);
        return personService.getPersonSearchResult(searchTerm);
    }
}
