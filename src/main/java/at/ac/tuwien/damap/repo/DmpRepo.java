package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Dmp;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DmpRepo implements PanacheRepository<Dmp> {

    public List<Dmp> getAll() {
        return listAll();
    }
}
