package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Identifier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class IdentifierRepo implements PanacheRepository<Identifier> {

    public List<Identifier> getAll() {
        return listAll();
    }
}
