package at.ac.tuwien.damap.rest.gdpr.service;

import at.ac.tuwien.damap.annotations.gdpr.Gdpr;
import at.ac.tuwien.damap.annotations.gdpr.GdprBase;
import at.ac.tuwien.damap.annotations.gdpr.GdprExtended;
import at.ac.tuwien.damap.annotations.gdpr.GdprKey;
import at.ac.tuwien.damap.repo.GdprRepo;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprResult;
import at.ac.tuwien.damap.rest.gdpr.domain.GdprQuery;
import at.ac.tuwien.damap.rest.gdpr.domain.HqlQuery;
import at.ac.tuwien.damap.rest.gdpr.exceptions.MissingGdprKeyException;
import org.reflections.Reflections;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Set<Class<?>> gdprClasses = reflections
                .getTypesAnnotatedWith(Gdpr.class);

        baseQueries = new ArrayList<>();
        extendedQueries = new ArrayList<>();
        // Get fields containing GDPR data
        for (Class<?> gdprClass : gdprClasses) {
            GdprQuery query = this.buildQueryObject(gdprClass);
            baseQueries.add(GdprQueryUtil.buildHqlQuery(query, false));
            extendedQueries.add(GdprQueryUtil.buildHqlQuery(query, true));
        }
    }

    private GdprQuery buildQueryObject(Class<?> gdprClass) throws MissingGdprKeyException {
        Field[] fields = gdprClass.getDeclaredFields();
        // Get all GDPR fields
        List<Field> gdprFields = Stream.of(fields).filter(f ->
                f.isAnnotationPresent(GdprKey.class)
                || f.isAnnotationPresent(GdprBase.class)
                || f.isAnnotationPresent(GdprExtended.class)
        ).collect(Collectors.toList());

        // Build query object for entity from fields
        GdprQuery query = new GdprQuery();
        query.setEntityName(gdprClass.getSimpleName());
        List<String> base = new ArrayList<>();
        List<String> extended = new ArrayList<>();
        List<GdprQuery> baseJoins = new ArrayList<>();
        List<GdprQuery> extendedJoins = new ArrayList<>();
        gdprFields.forEach(f -> {
            if (isPrimitiveOrEnum(f.getType())) {
                if (f.isAnnotationPresent(GdprKey.class)) {
                    query.setKey(getColumnName(f));
                } else if (f.isAnnotationPresent(GdprBase.class)) {
                    base.add(getColumnName(f));
                } else if (f.isAnnotationPresent(GdprExtended.class)) {
                    extended.add(getColumnName(f));
                }
            } else {
                // Build query object for joined entities
                GdprQuery join = this.getJoinQuery(f);
                if (f.isAnnotationPresent(GdprBase.class)) {
                    baseJoins.add(join);
                } else if (f.isAnnotationPresent(GdprExtended.class)) {
                    extendedJoins.add(join);
                }
            }
        });
        query.setBase(base);
        query.setExtended(extended);
        query.setBaseJoins(baseJoins);
        query.setExtendedJoins(extendedJoins);

        if (query.getKey() == null) {
            throw new MissingGdprKeyException("No @GdprKey found for entity " + query.getEntityName());
        }
        return query;
    }

    private GdprQuery getJoinQuery(Field field) {
        GdprQuery query = new GdprQuery();
        query.setEntityName(field.getName());
        query.setBase(new ArrayList<>());

        for (Field f : field.getType().getDeclaredFields()) {
            // Ignore transient & version columns
            if (!(f.isAnnotationPresent(Transient.class) || f.isAnnotationPresent(Version.class))) {
                query.getBase().add(getColumnName(f));
            }
        }
        return query;
    }

    private String getColumnName(Field field) {
        return field.getName();
    }

    private boolean isPrimitiveOrEnum(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz)
               || Integer.class.isAssignableFrom(clazz)
               || Long.class.isAssignableFrom(clazz)
               || Double.class.isAssignableFrom(clazz)
               || Float.class.isAssignableFrom(clazz)
               || Boolean.class.isAssignableFrom(clazz)
               || Date.class.isAssignableFrom(clazz)
               || clazz.isEnum();
    }

}

