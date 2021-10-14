package at.ac.tuwien.damap.rest.fits.service;

import at.ac.tuwien.damap.rest.fits.dto.MultipartBodyDTO;
import edu.harvard.fits.Fits;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("fits")
@RegisterRestClient(configKey = "rest.fits")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public interface FitsRestService {

    @POST
    @Path("/examine")
    Fits analyseFile(@MultipartForm MultipartBodyDTO datafile);
}
