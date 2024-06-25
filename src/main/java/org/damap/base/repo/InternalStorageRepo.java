package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.InternalStorage;

@ApplicationScoped
public class InternalStorageRepo implements PanacheRepository<InternalStorage> {

  public List<InternalStorage> getAll() {
    return listAll();
  }
}
