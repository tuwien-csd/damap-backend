package org.damap.base.rest.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.damap.base.repo.InternalStorageTranslationRepo;

/** InternalStorageService class. */
@ApplicationScoped
public class InternalStorageService {

  @Inject InternalStorageTranslationRepo internalStorageTranslationRepo;

  /**
   * getAllByLanguage.
   *
   * @param languageCode a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  public List<InternalStorageDO> getAllByLanguage(String languageCode) {
    List<InternalStorageDO> internalStorageDOList = new ArrayList<>();
    internalStorageTranslationRepo
        .getAllInternalStorageByLanguage(languageCode)
        .forEach(
            storageTranslation ->
                internalStorageDOList.add(
                    InternalStorageDOMapper.mapEntityToDO(
                        storageTranslation, new InternalStorageDO())));
    return internalStorageDOList;
  }
}
