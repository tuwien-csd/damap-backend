package org.damap.base.rest.openaire.service;

import generated.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.openaire.OpenAireRemoteResource;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@JBossLog
public class OpenAireService {

  @Inject @RestClient OpenAireRemoteResource openAireRemoteResource;

  public Response search(String doi) {
    return openAireRemoteResource.search(doi);
  }
}
