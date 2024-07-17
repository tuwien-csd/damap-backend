package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.InternalStorage;

/** InternalStorageRepo class. */
@ApplicationScoped
public class InternalStorageRepo implements PanacheRepository<InternalStorage> {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  public List<InternalStorage> getAll() {
    return listAll();
  }
}
