package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Funding;

/** FundingRepo class. */
@ApplicationScoped
public class FundingRepo implements PanacheRepository<Funding> {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  public List<Funding> getAll() {
    return listAll();
  }
}
