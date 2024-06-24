package org.damap.base.repo;

import org.damap.base.domain.Identifier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class IdentifierRepo implements PanacheRepository<Identifier> {

    public List<Identifier> getAll() {
        return listAll();
    }
}
