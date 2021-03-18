package at.ac.tuwien.rest.fits.dto;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class MultipartBodyDTO {

    @FormParam("datafile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File file;

    /*
    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;
    */
}
