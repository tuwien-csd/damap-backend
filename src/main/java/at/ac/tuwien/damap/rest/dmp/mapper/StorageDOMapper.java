package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Storage;
import at.ac.tuwien.damap.rest.dmp.domain.StorageDO;
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
