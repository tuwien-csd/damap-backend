package org.damap.base.rda.dmpcommonstandard;

import io.quarkus.arc.Arc;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.InternalStorage;
import org.damap.base.r3data.RepositoriesService;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.ExternalStorageDO;
import org.damap.base.rest.dmp.domain.RepositoryDO;
import org.damap.base.rest.dmp.domain.StorageDO;
import org.re3data.schema._2_2.Re3Data;

@JBossLog
/**
 * This class implements Host conversion from and to the RDA DMP Common Standard.
 *
 * <p>Note: While DAMAP uses inheritance for hosts (Repositories, External Storages, and Internal
 * Storages inheriting from a shared Host base), the RDA Common Standard utilizes a flat Host class
 * inside Distribution objects.
 */
public class HostsMapper extends AbstractMapper {
  /** Initialize the mapper with the default settings (strict mode turned on). */
  public HostsMapper() {
    this(true);
  }

  /**
   * Initialize the mapper with an option to turn off strict mode. Only use non-strict mode when a
   * human has to review the DMP afterward, never use it for automated imports!
   *
   * @param strict When enabled, any dropped data will cause a {@link
   *     CommonStandardCompatibilityException}. When disabled, DAMAP will do its best to represent
   *     any imported data in the common format but drop data it cannot represent.
   */
  public HostsMapper(boolean strict) {
    super(strict);
  }

  /**
   * DAMAP to RDA (Export).
   *
   * <p>Manages the mapping of DAMAP dataset hosts to the dataset's distributions. Searches across
   * repositories, external storages, and internal storages, applying fallback logic if no explicit
   * links are present.
   *
   * @param dmp the DAMAP DMP object containing host lists
   * @param datasetDO the DAMAP dataset being mapped
   * @param baseDist the base RDA standard distribution object containing basic metadata
   * @return a list of mapped RDA Distribution objects, each containing an assigned Host
   */
  public List<Distribution> mapDistributions(
      DmpDO dmp, DatasetDO datasetDO, Distribution baseDist) {

    List<Distribution> result = new ArrayList<>();
    if (baseDist == null || datasetDO == null) {
      return result;
    }

    String refHash = datasetDO.getReferenceHash();
    String idStr = String.valueOf(datasetDO.getId());

    // 1. Search and process Repository links
    searchRepositories(dmp, refHash, idStr, baseDist, result);

    // 2. Search and process External Storage links
    searchExternalStorages(dmp, refHash, idStr, baseDist, result);

    // 3. Search and process Internal Storage links
    searchInternalStorages(dmp, refHash, idStr, baseDist, result);

    // 4. Fallback if empty and only 1 dataset + 1 host
    if (result.isEmpty() && dmp.getDatasets() != null && dmp.getDatasets().size() == 1) {
      applySingleHostFallback(dmp, result, baseDist);
    }

    return result;
  }

  /**
   * Searches for repository hosts linked to the dataset, maps them, and adds them as Distribution
   * objects to the result list (retaining the availableUntil date). Skips the repository host if
   * there is no URL as it is a mandatory field.
   */
  private void searchRepositories(
      DmpDO dmp, String refHash, String idStr, Distribution baseDist, List<Distribution> result) {
    dmp.getRepositories().stream()
        .filter(repo -> isLinked(repo.getDatasets(), refHash, idStr))
        .forEach(
            repo -> {
              Host mappedHost = mapRepository(repo);
              if (mappedHost != null) {
                Distribution dist = result.isEmpty() ? baseDist : cloneDistribution(baseDist);
                dist.setHost(mappedHost);
                result.add(dist);
              }
            });
  }

  /**
   * Searches for external storage hosts linked to the dataset, maps them, clears the availableUntil
   * date (since it is not a repository), and adds them as Distribution objects.
   */
  private void searchExternalStorages(
      DmpDO dmp, String refHash, String idStr, Distribution baseDist, List<Distribution> result) {
    if (dmp.getExternalStorage() != null) {
      dmp.getExternalStorage().stream()
          .filter(ext -> isLinked(ext.getDatasets(), refHash, idStr))
          .forEach(
              ext -> {
                Distribution dist = result.isEmpty() ? baseDist : cloneDistribution(baseDist);
                dist.setHost(mapExternalStorage(ext));
                dist.setAvailableUntil(null);
                result.add(dist);
              });
    }
  }

  /**
   * Searches for internal storage hosts linked to the dataset, maps them, clears the availableUntil
   * date (since it is not a repository), and adds them as Distribution objects.
   */
  private void searchInternalStorages(
      DmpDO dmp, String refHash, String idStr, Distribution baseDist, List<Distribution> result) {
    if (dmp.getStorage() != null) {
      dmp.getStorage().stream()
          .filter(store -> isLinked(store.getDatasets(), refHash, idStr))
          .forEach(
              store -> {
                Distribution dist = result.isEmpty() ? baseDist : cloneDistribution(baseDist);
                dist.setHost(mapInternalStorage(store));
                dist.setAvailableUntil(null);
                result.add(dist);
              });
    }
  }

  /**
   * Fallback mapping mechanism designed for edge cases where datasets do not have explicit database
   * links to any hosts (repositories, internal storage, or external storage).
   *
   * <p>If the DMP contains exactly one dataset and exactly one host across all categories, this
   * method assumes an implicit 1-to-1 relationship and maps that single host to the dataset. If the
   * repository host has no URL, then it is skipped since that is a mandatory field.
   *
   * <ul>
   *   <li>Repositories retain the 'availableUntil' retention date from the base distribution.
   *   <li>Internal and external storages have their 'availableUntil' date cleared to null, as there
   *       is no support for it in the DAMAP model.
   * </ul>
   */
  private void applySingleHostFallback(
      DmpDO dmp, List<Distribution> result, Distribution baseDist) {
    if (dmp.getRepositories().size() == 1) {
      Host mappedHost = mapRepository(dmp.getRepositories().get(0));
      if (mappedHost != null) {
        baseDist.setHost(mappedHost);
        result.add(baseDist);
      }
    } else if (dmp.getExternalStorage() != null && dmp.getExternalStorage().size() == 1) {
      baseDist.setHost(mapExternalStorage(dmp.getExternalStorage().get(0)));
      baseDist.setAvailableUntil(null);
      result.add(baseDist);
    } else if (dmp.getStorage() != null && dmp.getStorage().size() == 1) {
      baseDist.setHost(mapInternalStorage(dmp.getStorage().get(0)));
      baseDist.setAvailableUntil(null);
      result.add(baseDist);
    }
  }

  /**
   * Helper method to determine if a storage/repository is linked to a specific dataset. Maps
   * dynamically on both transient reference hashes and persisted database IDs.
   *
   * @param datasets the list of dataset references on the host
   * @param refHash the transient reference hash of the dataset
   * @param idStr the database ID string of the dataset
   * @return true if the host is linked to the dataset, false otherwise
   */
  private boolean isLinked(List<String> datasets, String refHash, String idStr) {
    if (datasets == null) return false;
    return (idStr != null && datasets.contains(idStr))
        || (refHash != null && datasets.contains(refHash));
  }

  /**
   * DAMAP to RDA (Export).
   *
   * <p>Converts a DAMAP Repository domain object to an RDA standard Host. Attempts to fetch the
   * official URL and Title from the re3data service when possible.
   *
   * @param repo the DAMAP repository object
   * @return the mapped RDA standard Host or {@code null} if the repository URL is missing
   */
  private Host mapRepository(RepositoryDO repo) {
    String repoUrl = "https://google.com"; // Placeholder
    String repoTitle = repo.getTitle();
    if (repo.getRepositoryId() != null && !repo.getRepositoryId().isBlank()) {
      try {
        RepositoriesService repositoriesService =
            Arc.container().instance(RepositoriesService.class).get();
        Re3Data re3Data = repositoriesService.getById(repo.getRepositoryId());
        if (re3Data != null && !re3Data.getRepository().isEmpty()) {
          var firstRepo = re3Data.getRepository().get(0);
          String url = firstRepo.getRepositoryURL();
          if (url != null && !url.isBlank()) {
            repoUrl = url;
          }
          if (firstRepo.getRepositoryName() != null
              && firstRepo.getRepositoryName().getValue() != null
              && !firstRepo.getRepositoryName().getValue().isBlank()) {
            repoTitle = firstRepo.getRepositoryName().getValue();
          }
        }
      } catch (Exception e) {
        log.errorv(
            "Failed to retrieve repository name for ID {0}, error: {1}",
            repo.getRepositoryId(), e.getMessage());
      }
    }

    if (repoUrl == null || repoUrl.isBlank()) {
      log.warnv(
          "Repository '{0}' (ID: {1}) has no valid URL and will be skipped in the export.",
          repoTitle, repo.getRepositoryId());
      return null;
    }

    if (repoTitle == null || repoTitle.isBlank()) {
      repoTitle = "Repository";
    }
    return new Host().title(repoTitle).url(repoUrl);
  }

  /**
   * DAMAP to RDA (Export).
   *
   * <p>Converts a DAMAP External Storage domain object into an RDA standard Host.
   *
   * @param ext the DAMAP external storage object
   * @return the mapped RDA standard Host
   */
  private Host mapExternalStorage(ExternalStorageDO ext) {
    return new Host()
        .title(ext.getTitle() != null ? ext.getTitle() : "External Storage")
        // TODO: Map this to the real storage URL in the future (currently always defaults to
        // google.com)
        .url(ext.getUrl() != null && !ext.getUrl().isBlank() ? ext.getUrl() : "https://google.com")
        // TODO: This is misleading but technically allowed by the RDA Common Standard
        .backupType(ext.getBackupLocation())
        .backupFrequency(ext.getBackupFrequency());
  }

  /**
   * DAMAP to RDA (Export).
   *
   * <p>Converts a DAMAP Internal Storage domain object into an RDA standard Host. Attempts to load
   * specific backing Storage metadata (URL, Backup Location) if configured.
   *
   * @param store the DAMAP internal storage object
   * @return the mapped RDA standard Host
   */
  private Host mapInternalStorage(StorageDO store) {
    String url = "https://google.com"; // Placeholder
    String backupLocation = null;

    if (store.getInternalStorageId() != null) {
      try {
        InternalStorage internalStore = InternalStorage.findById(store.getInternalStorageId());
        if (internalStore != null) {
          if (internalStore.getUrl() != null && !internalStore.getUrl().isBlank()) {
            url = internalStore.getUrl();
          }
          if (internalStore.getBackupLocation() != null
              && !internalStore.getBackupLocation().isBlank()) {
            backupLocation = internalStore.getBackupLocation();
          }
        }
      } catch (Exception e) {
        log.errorv(
            "Failed to retrieve internal storage for ID {0}, error: {1}",
            store.getInternalStorageId(), e.getMessage());
      }
    }

    return new Host()
        .title(store.getTitle() != null ? store.getTitle() : "Storage")
        .url(url)
        // TODO: This is misleading but technically allowed by the RDA Common Standard
        .backupType(backupLocation);
  }

  /**
   * Clones a distribution object. Note: This helper method is maintained here because the {@link
   * Distribution} class is generated from the RDA Common Standard schema, preventing the addition
   * of a clone constructor.
   */
  private Distribution cloneDistribution(Distribution source) {
    Distribution target = new Distribution();
    target.setTitle(source.getTitle());
    target.setDataAccess(source.getDataAccess());
    target.setAvailableUntil(source.getAvailableUntil());
    target.setByteSize(source.getByteSize());
    target.setFormat(source.getFormat());
    target.setLicense(source.getLicense());
    return target;
  }

  /**
   * RDA to DAMAP (Import).
   *
   * <p>Imports an RDA standard Host object and maps it back onto DAMAP's storage structures.
   * Automatically reconstructs either an {@link ExternalStorageDO} (if an URL is present) or a
   * {@link RepositoryDO} and links it via reference hash.
   *
   * <p>Note: The referenceHash is required during the import phase because datasets are transient
   * at this stage and do not yet possess database IDs. The hash acts as a temporary identifier to
   * maintain relationships.
   *
   * @param target the target DMP to import the host into
   * @param rdaHost the source RDA standard host
   * @param refHash the reference hash of the dataset linked to this host
   */
  public void importHost(DmpDO target, Host rdaHost, String refHash) {
    String title = !rdaHost.getTitle().isBlank() ? rdaHost.getTitle() : "Imported Host";
    String url = rdaHost.getUrl();

    if (!url.isBlank()) {
      importAsExternalStorage(target, title, url, refHash, rdaHost);
    } else {
      importAsRepository(target, title, refHash);
    }
  }

  /**
   * Helper method to import an RDA Host as a DAMAP External Storage object. Reuses existing
   * external storage matches by title or appends a new one.
   *
   * @param target the target DMP
   * @param title the host title
   * @param url the host url
   * @param refHash the dataset's reference hash
   * @param rdaHost the RDA host metadata source
   */
  private void importAsExternalStorage(
      DmpDO target, String title, String url, String refHash, Host rdaHost) {
    if (target.getExternalStorage() == null) {
      target.setExternalStorage(new ArrayList<>());
    }

    ExternalStorageDO existingExt =
        target.getExternalStorage().stream()
            .filter(ext -> title.equalsIgnoreCase(ext.getTitle()))
            .findFirst()
            .orElse(null);

    if (existingExt != null) {
      if (!existingExt.getDatasets().contains(refHash)) {
        existingExt.getDatasets().add(refHash);
      }
    } else {
      var newExt = new ExternalStorageDO();
      newExt.setTitle(title);
      newExt.setUrl(url);
      newExt.setDatasets(new ArrayList<>(List.of(refHash)));
      newExt.setBackupFrequency(rdaHost.getBackupFrequency());
      newExt.setBackupLocation(rdaHost.getBackupType());
      target.getExternalStorage().add(newExt);
    }
  }

  /**
   * Helper method to import an RDA Host as a DAMAP Repository object. Reuses existing repository
   * matches by title or appends a new one.
   *
   * @param target the target DMP
   * @param title the host title
   * @param refHash the dataset's reference hash
   */
  private void importAsRepository(DmpDO target, String title, String refHash) {
    if (target.getRepositories() == null) {
      target.setRepositories(new ArrayList<>());
    }

    RepositoryDO existingRepo =
        target.getRepositories().stream()
            .filter(repo -> title.equalsIgnoreCase(repo.getTitle()))
            .findFirst()
            .orElse(null);

    if (existingRepo != null) {
      if (!existingRepo.getDatasets().contains(refHash)) {
        existingRepo.getDatasets().add(refHash);
      }
    } else {
      var newRepo = new RepositoryDO();
      newRepo.setTitle(title);
      newRepo.setDatasets(new ArrayList<>(List.of(refHash)));
      target.getRepositories().add(newRepo);
    }
  }
}
