package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.rest.addressbook.service.AddressBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/adb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    private static final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    AddressBookService addressBookService;

    @GET
    @Path("/person/{id}")
    public PersonDO getPersonById(@PathParam("id") String id) {
        log.info("Return person details for id=" + id);
        return addressBookService.getPersonById(id);
    }
}
