package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Dmp;

@ApplicationScoped
public class DmpRepo implements PanacheRepository<Dmp> {

  public List<Dmp> getAll() {
    return listAll();
  }
}
