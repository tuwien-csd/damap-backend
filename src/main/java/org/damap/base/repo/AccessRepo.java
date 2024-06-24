package org.damap.base.repo;

import org.damap.base.domain.Access;
import org.damap.base.domain.Dmp;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AccessRepo implements PanacheRepository<Access> {

    public List<Access> getAllDmpByUniversityId(String universityId) {
        return list("select access from Access access" +
                    " where access.universityId = :universityId ",
                    Parameters.with("universityId", universityId));
    }

    public List<Access> getAccessByDmp(Dmp dmp) {
        return list("select access from Access access" +
                    " where access.dmp = :dmp ",
                Parameters.with("dmp", dmp));
    }
}
