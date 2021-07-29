package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.rest.addressbook.dto.PersonDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface AddressBookRestService {

    @GET
    @Path("/person/v22/id/{id}")
    PersonDTO getPersonDetailsById(@PathParam String id);
}
