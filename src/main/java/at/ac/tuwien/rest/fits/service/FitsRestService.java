package at.ac.tuwien.rest.fits.service;

import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;
import at.ac.tuwien.rest.fits.dto.generated.Fits;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("fits")
@RegisterRestClient
public interface FitsRestService {

    @POST
    @Path("/examine")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    Fits analyseFile(@MultipartForm MultipartBodyDTO datafile);
}
