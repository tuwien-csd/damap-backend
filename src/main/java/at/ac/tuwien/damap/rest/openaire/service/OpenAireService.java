package at.ac.tuwien.damap.rest.openaire.service;

import at.ac.tuwien.damap.rest.openaire.OpenAireRemoteResource;
import generated.Response;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
