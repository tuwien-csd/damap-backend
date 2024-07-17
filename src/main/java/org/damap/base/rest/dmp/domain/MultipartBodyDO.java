package org.damap.base.rest.dmp.domain;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

/** MultipartBodyDO class. */
public class MultipartBodyDO {

  @FormParam("file")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  public InputStream file;
}
