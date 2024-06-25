package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Identifier;

@ApplicationScoped
public class IdentifierRepo implements PanacheRepository<Identifier> {

  public List<Identifier> getAll() {
    return listAll();
  }
}
