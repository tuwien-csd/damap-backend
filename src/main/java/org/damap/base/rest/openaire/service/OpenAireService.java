package org.damap.base.rest.openaire.service;

import generated.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.openaire.OpenAireRemoteResource;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/** OpenAireService class. */
@ApplicationScoped
@JBossLog
public class OpenAireService {

  @Inject @RestClient OpenAireRemoteResource openAireRemoteResource;

  /**
   * search.
   *
   * @param doi a {@link java.lang.String} object
   * @return a {@link generated.Response} object
   */
  public Response search(String doi) {
    return openAireRemoteResource.search(doi);
  }
}
