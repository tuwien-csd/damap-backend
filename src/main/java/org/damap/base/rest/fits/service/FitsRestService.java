package org.damap.base.rest.fits.service;

import edu.harvard.fits.Fits;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/** FitsRestService interface. */
@RegisterRestClient(configKey = "rest.fits")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public interface FitsRestService {

  /**
   * analyseFile.
   *
   * @param datafile a {@link org.damap.base.rest.fits.dto.MultipartBodyDTO} object
   * @return a {@link edu.harvard.fits.Fits} object
   */
  @POST
  @Path("/examine")
  Fits analyseFile(MultipartBodyDTO datafile);
}
