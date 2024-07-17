package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.DmpVersion;

/** DmpVersionRepo class. */
@ApplicationScoped
public class DmpVersionRepo implements PanacheRepository<DmpVersion> {

  /**
   * getAll.
   *
   * @return a {@link java.util.List} object
   */
  public List<DmpVersion> getAll() {
    return listAll();
  }

  /**
   * getAllByDmp.
   *
   * @param dmpId a long
   * @return a {@link java.util.List} object
   */
  public List<DmpVersion> getAllByDmp(long dmpId) {
    return list(
        "select version from DmpVersion version" + " where version.dmp.id = :dmpId ",
        Parameters.with("dmpId", dmpId));
  }
}
