package at.ac.tuwien.damap.rest.dmp.domain;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class MultipartBodyDO {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;
}
