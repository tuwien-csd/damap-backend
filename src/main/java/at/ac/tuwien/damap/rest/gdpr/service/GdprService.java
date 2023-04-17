package at.ac.tuwien.damap.rest.gdpr.service;

import at.ac.tuwien.damap.annotations.gdpr.*;
import at.ac.tuwien.damap.repo.GdprRepo;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprQuery;
import at.ac.tuwien.damap.rest.gdpr.domain.HqlQuery;
import org.hibernate.proxy.HibernateProxy;
import org.reflections.Reflections;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class GdprService {

    @Inject
    GdprRepo gdprRepo;

    private List<HqlQuery> baseQueries;
    private List<HqlQuery> extendedQueries;

    public GdprService() {
        this.init();
    }

    public List<GdprResult> getGdprBaseData(String universityId) {
        return gdprRepo.getGdprDataByUniversityId(baseQueries, universityId);
    }

    public List<GdprResult> getGdprExtendedData(String universityId) {
        return gdprRepo.getGdprDataByUniversityId(extendedQueries, universityId);
    }

    private void init() {
        String packageName = "at.ac.tuwien.damap.domain";
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> gdprClasses = reflections.getTypesAnnotatedWith(Gdpr.class);

        // Ignore HibernateProxy classes
        gdprClasses.removeIf(HibernateProxy.class::isAssignableFrom);

        // Get fields containing GDPR data
        baseQueries = new ArrayList<>();
        extendedQueries = new ArrayList<>();
        for (Class<?> gdprClass : gdprClasses) {
            GdprQuery query = GdprQueryUtil.buildQueryObject(gdprClass);
            baseQueries.add(HqlQueryUtil.buildHqlQuery(query, false));
            extendedQueries.add(HqlQueryUtil.buildHqlQuery(query, true));
        }
    }

}
