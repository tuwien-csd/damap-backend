package org.damap.base.repo;

import org.damap.base.rest.gdpr.domain.GdprResult;
import org.damap.base.rest.gdpr.domain.HqlQuery;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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
        gdprResult.setEntity(query.getEntityName());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> result = entityManager.createQuery(query.getHql(), (Class<Map<String, Object>>) (Class<?>) Map.class)
                .setParameter("id", universityId).getResultList();
        // Remove empty fields from maps (e.g. if Contributor -> personId_identifier = null)
        result.forEach(map -> map.values().removeIf(Objects::isNull));
        gdprResult.setEntries(result);

        return gdprResult;
    }

}
