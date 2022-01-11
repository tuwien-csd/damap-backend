package at.ac.tuwien.damap.r3data;

import generated.Repository;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.re3data.schema._2_2.Re3Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import at.ac.tuwien.damap.r3data.mapper.RepositoryMapper;

@ApplicationScoped
public class RepositoriesService {

    @Inject
    @RestClient
    RepositoriesRemoteResource repositoriesRemoteResource;

    @CacheResult(cacheName = "repositories")
    public List<Repository> getAll() {
        return repositoriesRemoteResource.getAll();
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
        return RepositoryMapper.mapToRepositoryDetails(getById(id)).getDescription();
    }

}
