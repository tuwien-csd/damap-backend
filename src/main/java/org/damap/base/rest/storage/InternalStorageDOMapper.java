package org.damap.base.rest.storage;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.InternalStorageTranslation;

@UtilityClass
public class InternalStorageDOMapper {

  public InternalStorageDO mapEntityToDO(
      InternalStorageTranslation storage, InternalStorageDO storageDO) {
    storageDO.setId(storage.getInternalStorageId().id);
    storageDO.setUrl(storage.getInternalStorageId().getUrl());
    storageDO.setBackupFrequency(storage.getBackupFrequency());
    storageDO.setStorageLocation(storage.getInternalStorageId().getStorageLocation());
    storageDO.setBackupLocation(storage.getInternalStorageId().getBackupLocation());

    storageDO.setLanguageCode(storage.getLanguageCode());
    storageDO.setTitle(storage.getTitle());
    storageDO.setDescription(storage.getDescription());

    return storageDO;
  }
}
