package org.damap.base.rest.dmp.mapper;

import org.damap.base.domain.Storage;
import org.damap.base.rest.dmp.domain.StorageDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageDOMapper {

    public StorageDO mapEntityToDO(Storage storage, StorageDO storageDO) {
        if (storage.getInternalStorageId() != null)
            storageDO.setInternalStorageId(storage.getInternalStorageId().id);
        return storageDO;
    }

    public Storage mapDOtoEntity(StorageDO storageDO, Storage storage, MapperService mapperService){
        if (storageDO.getInternalStorageId() != null)
            storage.setInternalStorageId(mapperService.getInternalStorageById(storageDO.getInternalStorageId()));
        return storage;
    }
}
