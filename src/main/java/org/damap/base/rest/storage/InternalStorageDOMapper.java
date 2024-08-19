package org.damap.base.rest.storage;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.damap.base.domain.InternalStorage;
import org.damap.base.domain.InternalStorageTranslation;

/** InternalStorageDOMapper class. */
@UtilityClass
public class InternalStorageDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param storage a {@link org.damap.base.domain.InternalStorageTranslation} object
   * @param storageDO a {@link org.damap.base.rest.storage.InternalStorageDO} object
   * @return a {@link org.damap.base.rest.storage.InternalStorageDO} object
   */
  public InternalStorageDO mapEntityToDO(
      InternalStorage storage,
      InternalStorageDO storageDO,
      List<InternalStorageTranslation> translations) {

    storageDO.setId(storage.id);
    storageDO.setUrl(storage.getUrl());
    storageDO.setStorageLocation(storage.getStorageLocation());
    storageDO.setBackupLocation(storage.getBackupLocation());
    storageDO.setActive(storage.isActive());

    List<InternalStorageTranslationDO> translationDOs = new ArrayList<>();
    for (InternalStorageTranslation translation : translations) {
      translationDOs.add(
          InternalStorageTranslationDOMapper.mapEntityToDO(
              translation, new InternalStorageTranslationDO()));
    }

    storageDO.setTranslations(translationDOs);

    return storageDO;
  }

  /**
   * mapDOToEntity.
   *
   * @param storageDO a {@link org.damap.base.rest.storage.InternalStorageDO} object
   * @param internalStorage a {@link org.damap.base.domain.InternalStorage} object
   * @return a {@link org.damap.base.domain.InternalStorage} object
   */
  public InternalStorage mapDOToEntity(
      InternalStorageDO storageDO, InternalStorage internalStorage) {
    internalStorage.setUrl(storageDO.getUrl());
    internalStorage.setStorageLocation(storageDO.getStorageLocation());
    internalStorage.setBackupLocation(storageDO.getBackupLocation());
    internalStorage.setActive(storageDO.getActive());
    return internalStorage;
  }
}
