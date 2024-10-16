package org.damap.base.repo;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.InternalStorage;
import org.damap.base.repo.base.RepoSearch;

/** InternalStorageRepo class. */
@ApplicationScoped
public class InternalStorageRepo implements RepoSearch<InternalStorage> {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  public List<InternalStorage> getAll() {
    return listAll();
  }
}
