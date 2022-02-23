package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.ExternalStorage;
import at.ac.tuwien.damap.rest.dmp.domain.ExternalStorageDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExternalStorageDOMapper {

    public ExternalStorageDO mapEntityToDO(ExternalStorage storage, ExternalStorageDO storageDO) {
        storageDO.setUrl(storage.getUrl());
        storageDO.setBackupFrequency(storage.getBackupFrequency());
        storageDO.setStorageLocation(storage.getStorageLocation());
        storageDO.setBackupLocation(storage.getBackupLocation());

        return storageDO;
    }

    public ExternalStorage mapDOtoEntity(ExternalStorageDO storageDO, ExternalStorage storage){
        storage.setUrl(storageDO.getUrl());
        storage.setBackupFrequency(storageDO.getBackupFrequency());
        storage.setStorageLocation(storageDO.getStorageLocation());
        storage.setBackupLocation(storageDO.getBackupLocation());

        return storage;
    }
}
