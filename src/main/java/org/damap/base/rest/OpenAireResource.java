package org.damap.base.rest;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.openaire.mapper.OpenAireMapper;
import org.damap.base.rest.openaire.service.OpenAireService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/api/openaire")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class OpenAireResource {

  @Inject OpenAireService openAireService;

  @GET
  public DatasetDO search(@QueryParam String doi) {
    log.info("Search for dataset with DOI: " + doi);
    return OpenAireMapper.mapAtoB(doi, openAireService.search(doi), new DatasetDO());
  }
}
