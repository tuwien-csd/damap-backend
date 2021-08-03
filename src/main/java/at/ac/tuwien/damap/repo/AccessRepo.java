package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Access;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AccessRepo implements PanacheRepository<Access> {

    public List<Access> getAllDmpByUniversityId(String universityId) {
        return list("select access from Access access" +
                    " where access.universityId = :universityId ",
                    Parameters.with("universityId", universityId));
    }
}
