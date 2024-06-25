package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.ExternalStorage;
import org.damap.base.rest.dmp.domain.ExternalStorageDO;

@UtilityClass
public class ExternalStorageDOMapper {

  public ExternalStorageDO mapEntityToDO(ExternalStorage storage, ExternalStorageDO storageDO) {
    storageDO.setUrl(storage.getUrl());
    storageDO.setBackupFrequency(storage.getBackupFrequency());
    storageDO.setStorageLocation(storage.getStorageLocation());
    storageDO.setBackupLocation(storage.getBackupLocation());

    return storageDO;
  }

  public ExternalStorage mapDOtoEntity(ExternalStorageDO storageDO, ExternalStorage storage) {
    storage.setUrl(storageDO.getUrl());
    storage.setBackupFrequency(storageDO.getBackupFrequency());
    storage.setStorageLocation(storageDO.getStorageLocation());
    storage.setBackupLocation(storageDO.getBackupLocation());

    return storage;
  }
}
