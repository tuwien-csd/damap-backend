package at.ac.tuwien.damap.rest.fits.service;

import at.ac.tuwien.damap.rest.fits.dto.MultipartBodyDTO;
import edu.harvard.fits.Fits;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "rest.fits")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public interface FitsRestService {

    @POST
    @Path("/examine")
    Fits analyseFile(@MultipartForm MultipartBodyDTO datafile);
}
