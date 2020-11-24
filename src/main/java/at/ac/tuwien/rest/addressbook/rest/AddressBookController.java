package at.ac.tuwien.rest.addressbook.rest;

import at.ac.tuwien.rest.addressbook.dto.DmpPerson;
import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnitDetails;
import at.ac.tuwien.rest.addressbook.service.AddressBookMapperServiceImpl;
import at.ac.tuwien.rest.addressbook.service.AddressBookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/adb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressBookController {
    private static final Logger log = LoggerFactory.getLogger(AddressBookController.class);

    @Inject
    @RestClient
    private AddressBookService addressBookService;

    @Inject
    private AddressBookMapperServiceImpl addressBookMapperService;

    @GET
    @Path("/person/{id}")
    public DmpPerson getPersonById(@PathParam("id") String id) {
        log.info("Return person details for id=" + id);
        return addressBookMapperService.getPersonById(id);
    }

    @GET
    @Path("/org/{id}")
    public OrganisationalUnitDetails getOrganisationalDetailsById(@PathParam("id") Long id) {
        log.info("Return organisational unit details for id=" + id);
        return addressBookService.getOrgDetailsById(id);
    }
}
