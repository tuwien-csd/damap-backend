package at.ac.tuwien.damap.rest.persons.orcid;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.arc.runtime.AdditionalBean;
import lombok.extern.jbosslog.JBossLog;

/*
    extend this class in your custom project, for your implementation
 */

@JBossLog
@AdditionalBean
public class ORCIDPersonServiceImpl implements PersonService {

    private String baseUrl;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public ORCIDPersonServiceImpl() {
        baseUrl = "pub.orcid.org";
    }

    @Override
    public ContributorDO getPersonById(String id) {
        return null;
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        List<ContributorDO> contributors = List.of();

        try {
            String url = String.join("&", constructUrl(
                    "/v3.0/expanded-search?q=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8.toString())),
                    "rows=10");
            var response = httpClient.send(
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(URI.create(url))
                            .header("accept", "application/json")
                            .timeout(Duration.ofSeconds(5))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new Exception(response.body());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            var resultsJson = objectMapper.readTree(response.body()).get("expanded-result");

            var contributorsOrcid = objectMapper.readValue(resultsJson.toString(),
                    new TypeReference<List<ContributorORCIDExpandedSearch>>() {
                    });

            if (contributorsOrcid != null) {
                contributors = contributorsOrcid.stream().map(o -> {
                    var c = new ContributorDO();
                    ContributorORCIDExpandedSearchMapper.mapEntityToDO(o, c);
                    return c;
                }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contributors;
    }

    private String constructUrl(String suffix) {
        return String.join("",
                "https://", baseUrl, suffix);
    }
}
