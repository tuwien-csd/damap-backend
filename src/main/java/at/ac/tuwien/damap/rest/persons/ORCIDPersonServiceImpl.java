package at.ac.tuwien.damap.rest.persons;

import static java.util.stream.Collectors.joining;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.runtime.AdditionalBean;
import lombok.extern.jbosslog.JBossLog;

/*
    extend this class in your custom project, for your implementation
 */

@JBossLog
@AdditionalBean
@DefaultBean
public class ORCIDPersonServiceImpl implements PersonService {
    @ConfigProperty(name = "damap.orcid.client.id")
    String clientID;

    @ConfigProperty(name = "damap.orcid.client.secret")
    String clientSecret;

    @ConfigProperty(name = "damap.orcid.testmode")
    boolean testmode;

    private ORCIDToken orcidToken = new ORCIDToken();

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public ORCIDPersonServiceImpl() {
        if (clientID != null && clientSecret != null) {
            try {
                orcidToken = getNewAuthToken();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public ContributorDO getPersonById(String id) {
        return null;
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        List<ContributorDO> contributors = List.of();

        if (orcidToken == null || orcidToken.hasExpired()) {
            // Try to get a new token.
            try {
                orcidToken = getNewAuthToken();
            } catch (Exception e) {
                log.error(e);
                return contributors;
            }
        }

        // Valid token available.
        log.info("Aquired token: " + orcidToken.accessToken);

        return contributors;
    }

    private ORCIDToken getNewAuthToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> requestBody = Map.ofEntries(
                Map.entry("client_id", clientID),
                Map.entry("client_secret", clientSecret),
                Map.entry("grant_type", "client_credentials"),
                Map.entry("scope", "/read-public"));

        // Construct params in body as form-urlencoded
        // (f.e. key1=value1&key2=value2)
        String requestBodyString = requestBody.entrySet().stream().map(entry -> {
            return String.join("",
                    entry.getKey(),
                    "=",
                    entry.getValue());
        })
                .collect(joining("&"));

        String url = testmode ? "https://sandbox.orcid.org/oauth/token" : "https://orcid.org/oauth/token";

        var response = httpClient.send(
                HttpRequest.newBuilder()
                        .setHeader("Content-Type", "application/x-www-form-urlencoded")
                        .POST(BodyPublishers.ofString(requestBodyString))
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(5))
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        // jakob fink, VPU Person API
        // stevanec-lausch, F&T Haus

        log.info("auth response: " + response.body());
        if (response.statusCode() >= 400) {
            log.info(response.request().bodyPublisher());
            throw new Exception(response.body());
        }
        return objectMapper.readValue(response.body(), ORCIDToken.class);
    }
}
