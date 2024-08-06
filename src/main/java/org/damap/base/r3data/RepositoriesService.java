package org.damap.base.r3data;

import generated.Repository;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.r3data.dto.RepositoryDetails;
import org.damap.base.r3data.mapper.RepositoryMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.re3data.schema._2_2.Re3Data;

/** RepositoriesService class. */
@JBossLog
@ApplicationScoped
public class RepositoriesService {

  @Inject @RestClient RepositoriesRemoteResource repositoriesRemoteResource;

  @ConfigProperty(name = "damap.repositories.recommendation")
  Optional<String[]> repositoriesRecommendation;

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  @CacheResult(cacheName = "repositories")
  public List<Repository> getAll() {
    return repositoriesRemoteResource.getAll();
  }

  /**
   * getRecommended.
   *
   * @return a {@link java.util.List} object
   */
  @CacheResult(cacheName = "recommendedRepositories")
  public List<RepositoryDetails> getRecommended() {
    List<RepositoryDetails> recommendedRepositories = new ArrayList<>();
    if (repositoriesRecommendation.isEmpty()) {
      return recommendedRepositories;
    }

    for (String id : repositoriesRecommendation.get()) {
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

  /**
   * getById.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link org.re3data.schema._2_2.Re3Data} object
   */
  @CacheResult(cacheName = "repository")
  public Re3Data getById(String id) {
    return repositoriesRemoteResource.getById(id);
  }

  /**
   * search.
   *
   * @param params a {@link jakarta.ws.rs.core.MultivaluedMap} object
   * @return a {@link java.util.List} object
   */
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
        subjects,
        contentTypes,
        countries,
        certificates,
        pidSystems,
        aidSystems,
        repositoryAccess,
        dataAccess,
        dataUpload,
        dataLicenses,
        repositoryTypes,
        institutionTypes,
        versioning,
        metadataStandards);
  }

  /**
   * getDescription.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   */
  public String getDescription(String id) {
    return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getDescription();
  }

  /**
   * getRepositoryURL.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   */
  public String getRepositoryURL(String id) {
    return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getRepositoryURL();
  }

  /**
   * getPidSystems.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  public List<EIdentifierType> getPidSystems(String id) {
    return RepositoryMapper.mapToRepositoryDetails(getById(id), id).getPidSystems();
  }
}
