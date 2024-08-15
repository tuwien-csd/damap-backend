package org.damap.base.rest.fits.dto;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import org.jboss.resteasy.reactive.PartFilename;
import org.jboss.resteasy.reactive.PartType;

/** MultipartBodyDTO class. */
public class MultipartBodyDTO {

  @FormParam("datafile")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  @PartFilename(
      "filename") // Adds filename to Content-Disposition header, because request fails without one
  public File file;
}
