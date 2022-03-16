package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.InternalStorageTranslation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class InternalStorageTranslationRepo implements PanacheRepository<InternalStorageTranslation> {

    public List<InternalStorageTranslation> getAllInternalStorageByLanguage(String languageCode) {
        return list("select storage from InternalStorageTranslation storage" +
                        " where storage.languageCode = :languageCode ",
                Parameters.with("languageCode", languageCode));
    }

    public InternalStorageTranslation getInternalStorageById(Long storageId, String languageCode) {
        return find("select storage from InternalStorageTranslation storage" +
                        " where storage.internalStorageId.id = :storageId " +
                        " and storage.languageCode = :languageCode ",
                Parameters.with("storageId", storageId).and("languageCode", languageCode)).firstResult();
    }
}
