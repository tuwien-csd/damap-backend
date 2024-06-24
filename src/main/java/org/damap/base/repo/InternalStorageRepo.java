package org.damap.base.repo;

import org.damap.base.domain.InternalStorage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class InternalStorageRepo implements PanacheRepository<InternalStorage> {

    public List<InternalStorage> getAll() {
        return listAll();
    }

}
