package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Storage;
import at.ac.tuwien.damap.rest.domain.StorageDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageDOMapper {

    public StorageDO mapEntityToDO(Storage storage, StorageDO storageDO) {
        storageDO.setUrl(storage.getUrl());
        storageDO.setBackupFrequency(storage.getBackupFrequency());
        storageDO.setStorageLocation(storage.getStorageLocation());
        storageDO.setBackupLocation(storage.getBackupLocation());

        return storageDO;
    }

    public Storage mapDOtoEntity(StorageDO storageDO, Storage storage){
        storage.setUrl(storageDO.getUrl());
        storage.setBackupFrequency(storageDO.getBackupFrequency());
        storage.setStorageLocation(storageDO.getStorageLocation());
        storage.setBackupLocation(storageDO.getBackupLocation());

        return storage;
    }
}
