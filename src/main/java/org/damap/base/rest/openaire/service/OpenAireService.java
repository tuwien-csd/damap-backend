package org.damap.base.rest.openaire.service;

import org.damap.base.rest.openaire.OpenAireRemoteResource;
import generated.Response;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@JBossLog
public class OpenAireService {

    @Inject
    @RestClient
    OpenAireRemoteResource openAireRemoteResource;

    public Response search(String doi) {
        return openAireRemoteResource.search(doi);
    }
}
