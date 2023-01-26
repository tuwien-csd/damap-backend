package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.domain.HqlQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;

@ApplicationScoped
public class GdprRepo {

    @Inject
    EntityManager entityManager;

    public List<GdprResult> getGdprDataByUniversityId(List<HqlQuery> queries, String universityId) {

        List<GdprResult> results = new ArrayList<>();
        for (HqlQuery query : queries) {
            results.add(this.getGdprEntityDataByUniversityId(query, universityId));
        }
        return results;
    }

    private GdprResult getGdprEntityDataByUniversityId(HqlQuery query, String universityId) {

        GdprResult gdprResult = new GdprResult();
        gdprResult.setEntity(query.getEntity());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> result = entityManager.createQuery(query.getHql(), (Class<Map<String, Object>>)(Class<?>) Map.class)
                .setParameter("id", universityId).getResultList();
        result.forEach(map -> map.values().removeIf(Objects::isNull));
        gdprResult.setEntries(result);

        return gdprResult;
    }

}
