package org.damap.base.rda.dmpcommonstandard;

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
/** This class implements Host conversion from and to the RDA DMP common standard. */
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

  // --- DAMAP -> RDA (Export) ---
  /**
   * Maps hosts to the dataset's distributions, automatically cloning distributions to accommodate
   * multiple hosts if necessary.
   */
  public List<Distribution> mapDistributions(
      DmpDO dmp,
      DatasetDO datasetDO,
      Distribution baseDist,
      RepositoriesService repositoriesService) {

    List<Distribution> result = new ArrayList<>();
    if (baseDist == null) {
      return result;
    }

    // Find all physical hosts linked to this dataset in DAMAP
    List<Host> matchedHosts = findAllHostsForDataset(dmp, datasetDO, repositoriesService);

    // If no hosts are configured, return the base distribution as-is
    if (matchedHosts.isEmpty()) {
      result.add(baseDist);
      return result;
    }

    // Process the first host on the base distribution
    Host firstHost = matchedHosts.get(0);
    baseDist.setHost(firstHost);
    if (!isRepositoryHost(firstHost, dmp)) {
      baseDist.setAvailableUntil(null); // Clear retention date for internal/external storage hosts
    }
    result.add(baseDist);

    // Process additional hosts by cloning the base distribution
    for (int i = 1; i < matchedHosts.size(); i++) {
      Host additionalHost = matchedHosts.get(i);
      Distribution clone = cloneDistribution(baseDist);
      clone.setHost(additionalHost);
      if (!isRepositoryHost(additionalHost, dmp)) {
        clone.setAvailableUntil(null); // Clear retention date for internal/external storage hosts
      }
      result.add(clone);
    }

    return result;
  }

  private List<Host> findAllHostsForDataset(
      DmpDO dmp, DatasetDO datasetDO, RepositoriesService repositoriesService) {

    List<Host> hosts = new ArrayList<>();
    if (datasetDO == null) {
      return hosts;
    }

    String refHash = datasetDO.getReferenceHash();
    String idStr = datasetDO.getId() != null ? String.valueOf(datasetDO.getId()) : null;

    // 1. Search Repositories
    if (dmp.getRepositories() != null) {
      dmp.getRepositories().stream()
          .filter(repo -> isLinked(repo.getDatasets(), refHash, idStr))
          .map(repo -> mapRepository(repo, repositoriesService))
          .forEach(hosts::add);
    }

    // 2. Search External Storage
    if (dmp.getExternalStorage() != null) {
      dmp.getExternalStorage().stream()
          .filter(ext -> isLinked(ext.getDatasets(), refHash, idStr))
          .map(this::mapExternalStorage)
          .forEach(hosts::add);
    }

    // 3. Search Internal Storage
    if (dmp.getStorage() != null) {
      dmp.getStorage().stream()
          .filter(store -> isLinked(store.getDatasets(), refHash, idStr))
          .map(this::mapInternalStorage)
          .forEach(hosts::add);
    }

    // 4. Fallback if empty and only 1 dataset + 1 host
    if (hosts.isEmpty() && dmp.getDatasets() != null && dmp.getDatasets().size() == 1) {
      applySingleHostFallback(dmp, repositoriesService, hosts);
    }

    return hosts;
  }

  private boolean isLinked(List<String> datasets, String refHash, String idStr) {
    if (datasets == null) return false;
    return (refHash != null && datasets.contains(refHash))
        || (idStr != null && datasets.contains(idStr));
  }

  private Host mapRepository(RepositoryDO repo, RepositoriesService repositoriesService) {
    String repoUrl = "https://google.com"; // Placeholder
    if (repo.getRepositoryId() != null && !repo.getRepositoryId().isBlank()) {
      try {
        Re3Data re3Data = repositoriesService.getById(repo.getRepositoryId());
        if (re3Data != null && !re3Data.getRepository().isEmpty()) {
          String url = re3Data.getRepository().get(0).getRepositoryURL();
          if (url != null && !url.isBlank()) {
            repoUrl = url;
          }
        }
      } catch (Exception e) {
        log.errorv(
            "Failed to retrieve repository name for ID {0}, error: {1}",
            repo.getRepositoryId(), e.getMessage());
      }
    }
    return new Host().title(repo.getTitle() != null ? repo.getTitle() : "Repository").url(repoUrl);
  }

  private Host mapExternalStorage(ExternalStorageDO ext) {
    return new Host()
        .title(ext.getTitle() != null ? ext.getTitle() : "External Storage")
        // TODO: Map this to the real storage URL in the future (currently always defaults to
        // google.com)
        .url(ext.getUrl() != null && !ext.getUrl().isBlank() ? ext.getUrl() : "https://google.com")
        // TODO: This is misleading but technically allowed by the RDA common standard [2]
        .backupType(ext.getBackupLocation())
        .backupFrequency(ext.getBackupFrequency());
  }

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
        // TODO: This is misleading but technically allowed by the RDA common standard
        .backupType(backupLocation);
  }

  private void applySingleHostFallback(
      DmpDO dmp, RepositoriesService repositoriesService, List<Host> hosts) {
    if (dmp.getRepositories() != null && dmp.getRepositories().size() == 1) {
      hosts.add(mapRepository(dmp.getRepositories().get(0), repositoriesService));
    } else if (dmp.getExternalStorage() != null && dmp.getExternalStorage().size() == 1) {
      hosts.add(mapExternalStorage(dmp.getExternalStorage().get(0)));
    } else if (dmp.getStorage() != null && dmp.getStorage().size() == 1) {
      hosts.add(mapInternalStorage(dmp.getStorage().get(0)));
    }
  }

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

  // --- RDA -> DAMAP (Import) ---
  public void importHost(DmpDO target, Host rdaHost, String refHash) {
    String title = !rdaHost.getTitle().isBlank() ? rdaHost.getTitle() : "Imported Host";
    String url = rdaHost.getUrl();

    if (!url.isBlank()) {
      importAsExternalStorage(target, title, url, refHash, rdaHost);
    } else {
      importAsRepository(target, title, refHash);
    }
  }

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

  public boolean isRepositoryHost(Host host, DmpDO dmp) {
    if (host == null || dmp.getRepositories() == null) {
      return false;
    }
    return dmp.getRepositories().stream()
        .anyMatch(repo -> host.getTitle().equalsIgnoreCase(repo.getTitle()));
  }
}
