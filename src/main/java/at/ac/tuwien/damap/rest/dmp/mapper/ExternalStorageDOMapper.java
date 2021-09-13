package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.ExternalStorage;
import at.ac.tuwien.damap.rest.dmp.domain.StorageDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExternalStorageDOMapper {

    public StorageDO mapEntityToDO(ExternalStorage storage, StorageDO storageDO) {
        storageDO.setUrl(storage.getUrl());
        storageDO.setBackupFrequency(storage.getBackupFrequency());
        storageDO.setStorageLocation(storage.getStorageLocation());
        storageDO.setBackupLocation(storage.getBackupLocation());

        return storageDO;
    }

    public ExternalStorage mapDOtoEntity(StorageDO storageDO, ExternalStorage storage){
        storage.setUrl(storageDO.getUrl());
        storage.setBackupFrequency(storageDO.getBackupFrequency());
        storage.setStorageLocation(storageDO.getStorageLocation());
        storage.setBackupLocation(storageDO.getBackupLocation());

        return storage;
    }
}
