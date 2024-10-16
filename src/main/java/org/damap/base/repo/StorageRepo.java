package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.InternalStorage;
import org.damap.base.domain.Storage;

/** InternalStorageRepo class. */
@ApplicationScoped
public class StorageRepo implements PanacheRepository<Storage> {

  public List<Storage> findByInternalStorageId(InternalStorage internalStorage) {
    return list("internalStorageId", internalStorage);
  }
}
