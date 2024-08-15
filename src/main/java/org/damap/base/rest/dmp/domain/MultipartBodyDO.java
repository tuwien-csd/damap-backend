package org.damap.base.rest.dmp.domain;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import org.jboss.resteasy.reactive.PartType;

/** MultipartBodyDO class. */
public class MultipartBodyDO {

  @FormParam("file")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  public File file;
}
