package org.damap.base.rest.storage;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.InternalStorage;
import org.damap.base.domain.InternalStorageTranslation;

@UtilityClass
public class InternalStorageTranslationDOMapper {

  public InternalStorageTranslationDO mapEntityToDO(
      InternalStorageTranslation internalStorageTranslation,
      InternalStorageTranslationDO internalStorageTranslationDO) {

    internalStorageTranslationDO.setId(internalStorageTranslation.id);
    internalStorageTranslationDO.setStorageId(internalStorageTranslation.getInternalStorageId().id);
    internalStorageTranslationDO.setLanguageCode(internalStorageTranslation.getLanguageCode());
    internalStorageTranslationDO.setTitle(internalStorageTranslation.getTitle());
    internalStorageTranslationDO.setDescription(internalStorageTranslation.getDescription());
    internalStorageTranslationDO.setBackupFrequency(
        internalStorageTranslation.getBackupFrequency());

    return internalStorageTranslationDO;
  }

  public InternalStorageTranslation mapDOToTranslationEntityForCreation(
      InternalStorageTranslationDO translationDO, InternalStorage internalStorage) {
    InternalStorageTranslation translation = new InternalStorageTranslation();
    translation.setLanguageCode(translationDO.getLanguageCode());
    translation.setTitle(translationDO.getTitle());
    translation.setDescription(translationDO.getDescription());
    translation.setBackupFrequency(translationDO.getBackupFrequency());
    translation.setInternalStorageId(internalStorage);
    return translation;
  }

  public InternalStorageTranslation mapDOToEntity(
      InternalStorageTranslationDO translationDO, InternalStorageTranslation translation) {
    translation.setLanguageCode(translationDO.getLanguageCode());
    translation.setTitle(translationDO.getTitle());
    translation.setDescription(translationDO.getDescription());
    translation.setBackupFrequency(translationDO.getBackupFrequency());
    return translation;
  }
}
