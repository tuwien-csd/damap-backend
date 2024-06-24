package org.damap.base.repo;

import org.damap.base.domain.Dmp;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DmpRepo implements PanacheRepository<Dmp> {

    public List<Dmp> getAll() {
        return listAll();
    }
}
