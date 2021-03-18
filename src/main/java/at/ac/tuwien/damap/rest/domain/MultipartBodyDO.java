package at.ac.tuwien.damap.rest.domain;

import java.io.File;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartBodyDO {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File file;

    /*
    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;
     */
}
