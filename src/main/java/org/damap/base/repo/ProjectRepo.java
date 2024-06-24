package org.damap.base.repo;

import org.damap.base.domain.Project;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProjectRepo implements PanacheRepository<Project> {

    public List<Project> getAll() {
        return listAll();
    }
}
