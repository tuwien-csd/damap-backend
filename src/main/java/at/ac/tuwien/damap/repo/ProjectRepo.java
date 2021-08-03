package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Project;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProjectRepo implements PanacheRepository<Project> {

    public List<Project> getAll() {
        return listAll();
    }
}
