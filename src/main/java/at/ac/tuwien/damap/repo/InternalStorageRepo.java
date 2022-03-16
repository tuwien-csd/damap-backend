package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.InternalStorage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class InternalStorageRepo implements PanacheRepository<InternalStorage> {

    public List<InternalStorage> getAll() {
        return listAll();
    }

}
