package at.ac.tuwien.damap.rest.storage;

import at.ac.tuwien.damap.repo.InternalStorageTranslationRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InternalStorageService {

    @Inject
    InternalStorageTranslationRepo internalStorageTranslationRepo;

    public List<InternalStorageDO> getAllByLanguage(String languageCode) {
        List<InternalStorageDO> internalStorageDOList = new ArrayList<>();
        internalStorageTranslationRepo.getAllInternalStorageByLanguage(languageCode).forEach(storageTranslation ->
                internalStorageDOList.add(InternalStorageDOMapper.mapEntityToDO(storageTranslation, new InternalStorageDO())));
        return internalStorageDOList;
    }
}
