package org.damap.base.rest.invenio_damap;

import java.util.*;
import lombok.experimental.UtilityClass;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.enums.EAccessRight;
import org.damap.base.enums.EDataAccessType;
import org.damap.base.enums.EDataKind;
import org.damap.base.enums.EDataSource;
import org.damap.base.enums.EDataType;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.enums.ELicense;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.ExternalStorageDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import org.damap.base.rest.madmp.dto.Dataset;
import org.damap.base.rest.madmp.dto.Distribution;
import org.damap.base.rest.madmp.dto.Host;

@JBossLog
@UtilityClass
public class InvenioDamapResourceMapper {
  public DatasetDO mapMaDMPDatasetToDatasetDO(
      Dataset madmpDataset, DatasetDO datasetDO, DmpDO dmpDO) {

    // Disclaimer: This is by no means complete. Not all fields of the
    // Dataset or DMP are set. Null value checks should also be performed.
    var datasetId = madmpDataset.getDatasetId();
    if (datasetId != null) {
      IdentifierDO newId = new IdentifierDO();
      newId.setIdentifier(datasetId.getIdentifier());
      newId.setType(EIdentifierType.valueOf(datasetId.getType().name().toUpperCase()));
      datasetDO.setDatasetId(newId);
    }

    if (datasetDO.getReferenceHash() != null) {
      datasetDO.setReferenceHash("invenio" + new Date());
    }

    datasetDO.setDateOfDeletion(null);
    datasetDO.setDelete(false);
    datasetDO.setDeletionPerson(null);

    if (madmpDataset.getDescription() != null && !madmpDataset.getDescription().isEmpty()) {
      datasetDO.setDescription(madmpDataset.getDescription());
    }

    mapDistribution(madmpDataset, datasetDO, dmpDO);

    if (datasetDO.getOtherProjectMembersAccess() == null) {
      datasetDO.setOtherProjectMembersAccess(EAccessRight.READ);
    }

    if (madmpDataset.getPersonalData() != null || datasetDO.getPersonalData() == null) {
      Boolean personalData =
          switch (Objects.requireNonNullElse(
              madmpDataset.getPersonalData(), Dataset.PersonalData.UNKNOWN)) {
            case NO -> false;
            default -> true;
          };
      datasetDO.setPersonalData(personalData);
      dmpDO.setPersonalData(dmpDO.getPersonalData() || personalData);
    }

    if (datasetDO.getPublicAccess() == null) {
      datasetDO.setPublicAccess(EAccessRight.READ);
    }
    if (datasetDO.getSelectedProjectMembersAccess() == null) {
      datasetDO.setSelectedProjectMembersAccess(EAccessRight.READ);
    }

    if (madmpDataset.getSensitiveData() != null || datasetDO.getSensitiveData() == null) {
      Boolean sensitiveData =
          switch (Objects.requireNonNullElse(
              madmpDataset.getSensitiveData(), Dataset.SensitiveData.UNKNOWN)) {
            case NO -> false;
            default -> true;
          };
      datasetDO.setSensitiveData(sensitiveData);
      dmpDO.setSensitiveData(dmpDO.getSensitiveData() || sensitiveData);
    }

    // TODO: Let user decide?
    if (datasetDO.getSource() == null) {
      datasetDO.setSource(EDataSource.NEW);
      // This should match the dataset. If new, setDataKind. else setReusedDataKind
      dmpDO.setDataKind(EDataKind.SPECIFY);
      dmpDO.setReusedDataKind(EDataKind.SPECIFY);
    }

    if (madmpDataset.getTitle() != null) {
      datasetDO.setTitle(madmpDataset.getTitle());
    }

    if (madmpDataset.getType() != null) {
      // Setting data type
      EDataType type = EDataType.getByValue(madmpDataset.getType());
      if (type == null) {
        log.info("Could not infer EDataType from provided value: " + madmpDataset.getType());
      } else {
        var types = Objects.requireNonNullElse(datasetDO.getType(), new ArrayList<EDataType>());
        if (!types.contains(type)) {
          types.add(type);
        }
        datasetDO.setType(types);
      }
    }

    return datasetDO;
  }

  private static void mapDistribution(Dataset madmpDataset, DatasetDO datasetDO, DmpDO dmpDO) {
    if (madmpDataset.getDistribution() == null) {
      return;
    }

    Set<String> licenses = new HashSet<>();
    for (Distribution d : madmpDataset.getDistribution()) {
      if (d.getDataAccess() != null) {
        var dataAccess = EDataAccessType.getByValue(d.getDataAccess().value());
        if (dataAccess != null && dataAccess.compare(datasetDO.getDataAccess()) == 1) {
          datasetDO.setDataAccess(dataAccess);
        }
      }
      licenses.addAll(d.getLicense().stream().map(l -> l.getLicenseRef().toString()).toList());

      if (d.getByteSize() != null
          && d.getByteSize() > Objects.requireNonNullElse(datasetDO.getSize(), 0L)) {
        datasetDO.setSize(d.getByteSize().longValue());
      }

      if (d.getHost() == null) {
        // Nothing more to do
        continue;
      }

      Host host = d.getHost();
      String hostPath = host.getUrl() == null ? null : host.getUrl().getPath();

      // Check if the provided storage is already set on the DMP.
      var externalStorages = dmpDO.getExternalStorage();
      ExternalStorageDO externalStorageDO =
          externalStorages.stream()
              .filter(s -> s.getUrl() != null && s.getUrl().equals(hostPath))
              .findFirst()
              .orElse(null);

      if (externalStorageDO == null) {
        externalStorageDO = new ExternalStorageDO();
        externalStorageDO.setBackupFrequency(host.getBackupFrequency());
        externalStorageDO.setStorageLocation(
            host.getGeoLocation() != null ? host.getGeoLocation().toString() : null);
        externalStorageDO.setTitle(host.getTitle());
        externalStorageDO.setUrl(hostPath);
        externalStorages.add(externalStorageDO);
      }

      var datasetHashes = externalStorageDO.getDatasets();
      if (datasetHashes.contains(datasetDO.getReferenceHash())) {
        datasetHashes.add(datasetDO.getReferenceHash());
      }
    }

    // TODO: Support multiple licenses
    for (String license : licenses) {
      for (ELicense eLicense : ELicense.values()) {
        if (license.equals(eLicense.getUrl())) {
          datasetDO.setLicense(eLicense);
          break;
        }
      }
    }
  }
}
