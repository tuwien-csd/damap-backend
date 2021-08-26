package at.ac.tuwien.damap.rest.fits.dto;

import org.jboss.resteasy.annotations.providers.multipart.PartFilename;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class MultipartBodyDTO {

    @FormParam("datafile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @PartFilename("filename") // Adds filename to Content-Disposition header, because request fails without one
    public InputStream file;
}
