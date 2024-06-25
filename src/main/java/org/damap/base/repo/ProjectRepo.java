package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.damap.base.domain.Project;

@ApplicationScoped
public class ProjectRepo implements PanacheRepository<Project> {

  public List<Project> getAll() {
    return listAll();
  }
}
