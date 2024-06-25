package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Funding;

@ApplicationScoped
public class FundingRepo implements PanacheRepository<Funding> {

  public List<Funding> getAll() {
    return listAll();
  }
}
