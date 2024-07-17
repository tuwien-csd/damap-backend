package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Identifier;

/** IdentifierRepo class. */
@ApplicationScoped
public class IdentifierRepo implements PanacheRepository<Identifier> {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  public List<Identifier> getAll() {
    return listAll();
  }
}
