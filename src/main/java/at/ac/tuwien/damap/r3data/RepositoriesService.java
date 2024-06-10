package at.ac.tuwien.damap.r3data;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.r3data.dto.RepositoryDetails;
import at.ac.tuwien.damap.r3data.mapper.RepositoryMapper;
import generated.Repository;
import io.quarkus.cache.CacheResult;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.re3data.schema._2_2.Re3Data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

@JBossLog
@ApplicationScoped
public class RepositoriesService {

    @Inject
    @RestClient
    RepositoriesRemoteResource repositoriesRemoteResource;

    @ConfigProperty(name = "damap.repositories.recommendation")
    String[] repositoriesRecommendation;

    @CacheResult(cacheName = "repositories")
    public List<Repository> getAll() {
        return repositoriesRemoteResource.getAll();
    }

    @CacheResult(cacheName = "recommendedRepositories")
    public List<RepositoryDetails> getRecommended() {
        List<RepositoryDetails> recommendedRepositories = new ArrayList<>();
        for (String id : repositoriesRecommendation) {
            if (id.startsWith("r3d")) {
                try {
                    Re3Data repo = this.getById(id);
                    recommendedRepositories.add(RepositoryMapper.mapToRepositoryDetails(repo, id));
                } catch (Exception e) {
                    log.infov("Failed to retrieve repository for ID {0}, error: {1}", id, e.getMessage());
                }
            }
        }
        return recommendedRepositories;
    }

    @CacheResult(cacheName = "repository")
    public Re3Data getById(String id) {
        return repositoriesRemoteResource.getById(id);
    }

    public List<Repository> search(MultivaluedMap<String, String> params) {
        List<String> subjects = params.get("subjects");
        List<String> contentTypes = params.get("contentTypes");
        List<String> certificates = params.get("certificates");
        List<String> countries = params.get("countries");
        List<String> pidSystems = params.get("pidSystems");
        List<String> aidSystems = params.get("aidSystems");
        List<String> repositoryAccess = params.get("repositoryAccess");
        List<String> dataAccess = params.get("dataAccess");
        List<String> dataUpload = params.get("dataUpload");
        List<String> dataLicenses = params.get("dataLicenses");
        List<String> repositoryTypes = params.get("repositoryTypes");
        List<String> institutionTypes = params.get("institutionTypes");
        List<String> versioning = params.get("versioning");
        List<String> metadataStandards = params.get("metadataStandards");
        return repositoriesRemoteResource.search(
                subjects, contentTypes, countries, certificates, pidSystems,
                aidSystems, repositoryAccess, dataAccess, dataUpload, dataLicenses,
                repositoryTypes, institutionTypes, versioning, metadataStandards);
    }

    public String getDescription(String id) {
        return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getDescription();
    }

    public String getRepositoryURL(String id) {
        return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getRepositoryURL();
    }

    public List<EIdentifierType> getPidSystems(String id) {
        return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getPidSystems();
    }
}
