package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Access;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AccessRepo implements PanacheRepository<Access> {

    public List<Access> getAllDmpByUniversityId(long universityId) {
        return find("select access from Access access" +
                " where university_id = " + universityId).list();
    }
}
