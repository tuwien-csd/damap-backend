package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.rest.addressbook.dto.OrganisationalUnitDetails;
import at.ac.tuwien.rest.addressbook.dto.PersonDetails;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api")
@RegisterRestClient
public interface AddressBookService {


    @GET
    @Path("/person/v22/id/{id}")
    @Produces("application/json")
    PersonDetails getPersonDetailsById(@PathParam String id);

    @GET
    @Path("/orgunit/v22/id/{id}")
    @Produces("application/json")
    OrganisationalUnitDetails getOrgDetailsById(@PathParam Long id);
}
