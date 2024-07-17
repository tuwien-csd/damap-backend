package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.InternalStorageTranslation;

/** InternalStorageTranslationRepo class. */
@ApplicationScoped
public class InternalStorageTranslationRepo
    implements PanacheRepository<InternalStorageTranslation> {

  /**
   * getAllInternalStorageByLanguage.
   *
   * @param languageCode a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  public List<InternalStorageTranslation> getAllInternalStorageByLanguage(String languageCode) {
    return list(
        "select storage from InternalStorageTranslation storage"
            + " where storage.languageCode = :languageCode ",
        Parameters.with("languageCode", languageCode));
  }

  /**
   * getInternalStorageById.
   *
   * @param storageId a {@link java.lang.Long} object
   * @param languageCode a {@link java.lang.String} object
   * @return a {@link org.damap.base.domain.InternalStorageTranslation} object
   */
  public InternalStorageTranslation getInternalStorageById(Long storageId, String languageCode) {
    return find(
            "select storage from InternalStorageTranslation storage"
                + " where storage.internalStorageId.id = :storageId "
                + " and storage.languageCode = :languageCode ",
            Parameters.with("storageId", storageId).and("languageCode", languageCode))
        .firstResult();
  }
}
