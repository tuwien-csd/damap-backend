package org.damap.base.rest.gdpr.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;
import org.damap.base.annotations.gdpr.*;
import org.damap.base.repo.GdprRepo;
import org.damap.base.rest.gdpr.domain.GdprQuery;
import org.damap.base.rest.gdpr.domain.GdprResult;
import org.damap.base.rest.gdpr.domain.HqlQuery;
import org.hibernate.proxy.HibernateProxy;
import org.reflections.Reflections;

/** GdprService class. */
@ApplicationScoped
public class GdprService {

  @Inject GdprRepo gdprRepo;

  private List<HqlQuery> baseQueries;
  private List<HqlQuery> extendedQueries;

  /** Constructor for GdprService. */
  public GdprService() {
    this.init();
  }

  /**
   * getGdprBaseData.
   *
   * @param universityId a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  public List<GdprResult> getGdprBaseData(String universityId) {
    return gdprRepo.getGdprDataByUniversityId(baseQueries, universityId);
  }

  /**
   * getGdprExtendedData.
   *
   * @param universityId a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   */
  public List<GdprResult> getGdprExtendedData(String universityId) {
    return gdprRepo.getGdprDataByUniversityId(extendedQueries, universityId);
  }

  private void init() {
    String packageName = "org.damap.base.domain";
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
