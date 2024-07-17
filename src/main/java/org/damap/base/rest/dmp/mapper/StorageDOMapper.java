package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Storage;
import org.damap.base.rest.dmp.domain.StorageDO;

/** StorageDOMapper class. */
@UtilityClass
public class StorageDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param storage a {@link org.damap.base.domain.Storage} object
   * @param storageDO a {@link org.damap.base.rest.dmp.domain.StorageDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.StorageDO} object
   */
  public StorageDO mapEntityToDO(Storage storage, StorageDO storageDO) {
    if (storage.getInternalStorageId() != null)
      storageDO.setInternalStorageId(storage.getInternalStorageId().id);
    return storageDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param storageDO a {@link org.damap.base.rest.dmp.domain.StorageDO} object
   * @param storage a {@link org.damap.base.domain.Storage} object
   * @param mapperService a {@link org.damap.base.rest.dmp.mapper.MapperService} object
   * @return a {@link org.damap.base.domain.Storage} object
   */
  public Storage mapDOtoEntity(StorageDO storageDO, Storage storage, MapperService mapperService) {
    if (storageDO.getInternalStorageId() != null)
      storage.setInternalStorageId(
          mapperService.getInternalStorageById(storageDO.getInternalStorageId()));
    return storage;
  }
}
