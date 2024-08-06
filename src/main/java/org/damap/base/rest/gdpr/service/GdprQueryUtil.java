package org.damap.base.rest.gdpr.service;

import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.annotations.gdpr.GdprBase;
import org.damap.base.annotations.gdpr.GdprContext;
import org.damap.base.annotations.gdpr.GdprExtended;
import org.damap.base.annotations.gdpr.GdprKey;
import org.damap.base.rest.gdpr.domain.GdprQuery;
import org.damap.base.rest.gdpr.exceptions.MissingGdprKeyException;
import org.damap.base.rest.gdpr.exceptions.NoSuchContextPropertyException;

/** GdprQueryUtil class. */
@UtilityClass
@JBossLog
public class GdprQueryUtil {

  /**
   * Extracts all GDPR relevant information from the given class and stores them in a {@link
   * GdprQuery} object (which will later be used to build the HQL query).
   *
   * @param gdprClass class to extract information from.
   * @return {@link org.damap.base.rest.gdpr.domain.GdprQuery} object containing all information
   *     necessary to build HQL query.
   * @throws org.damap.base.rest.gdpr.exceptions.MissingGdprKeyException if the given class is
   *     missing a {@link org.damap.base.annotations.gdpr.GdprKey} annotation.
   * @throws org.damap.base.rest.gdpr.exceptions.NoSuchContextPropertyException if a property, tried
   *     to be accessed via {@link org.damap.base.annotations.gdpr.GdprContext}, does not exist.
   */
  public GdprQuery buildQueryObject(Class<?> gdprClass)
      throws MissingGdprKeyException, NoSuchContextPropertyException {

    List<Field> gdprFields = getGdprFields(gdprClass.getDeclaredFields());

    // Build query object for entity from fields

    GdprQuery query = new GdprQuery();
    query.setRoot(gdprClass);
    List<String> base = new ArrayList<>();
    List<String> extended = new ArrayList<>();
    List<String> context = new ArrayList<>();
    List<GdprQuery> baseJoins = new ArrayList<>();
    List<GdprQuery> extendedJoins = new ArrayList<>();
    List<GdprQuery> contextJoins = new ArrayList<>();
    gdprFields.forEach(
        f -> {
          if (isPrimitiveOrEnum(f.getType())) {
            if (f.isAnnotationPresent(GdprKey.class)) {
              query.setKey(getColumnName(f));
            } else if (f.isAnnotationPresent(GdprContext.class)) {
              context.add(getColumnName(f));
            } else if (f.isAnnotationPresent(GdprBase.class)) {
              base.add(getColumnName(f));
            } else if (f.isAnnotationPresent(GdprExtended.class)) {
              extended.add(getColumnName(f));
            }
          } else {
            // Build query object for joined entities
            GdprQuery join = getJoinQuery(f);
            if (f.isAnnotationPresent(GdprContext.class)) {
              String[] properties = f.getAnnotation(GdprContext.class).properties();
              contextJoins.add(getContextJoinQuery(f, Arrays.asList(properties)));
            } else if (f.isAnnotationPresent(GdprBase.class)) {
              baseJoins.add(join);
            } else if (f.isAnnotationPresent(GdprExtended.class)) {
              extendedJoins.add(join);
            }
          }
        });
    query.setContext(context);
    query.setBase(base);
    query.setExtended(extended);
    query.setContextJoins(contextJoins);
    query.setBaseJoins(baseJoins);
    query.setExtendedJoins(extendedJoins);

    if (query.getKey() == null) {
      throw new MissingGdprKeyException(
          "No @GdprKey found for entity " + query.getRoot().getSimpleName());
    }
    return query;
  }

  private List<Field> getGdprFields(Field[] fields) {
    return Stream.of(fields)
        .filter(
            f ->
                f.isAnnotationPresent(GdprKey.class)
                    || f.isAnnotationPresent(GdprContext.class)
                    || f.isAnnotationPresent(GdprBase.class)
                    || f.isAnnotationPresent(GdprExtended.class))
        .collect(Collectors.toList());
  }

  // Simple join
  private GdprQuery getJoinQuery(Field field) {
    GdprQuery query = new GdprQuery();
    query.setFieldName(field.getName());
    query.setBase(new ArrayList<>());

    for (Field f : field.getType().getDeclaredFields()) {
      // Ignore transient & version columns
      if (!(f.isAnnotationPresent(Transient.class) || f.isAnnotationPresent(Version.class))) {
        query.getBase().add(getColumnName(f));
      }
    }
    return query;
  }

  // Recursive method used to join multiple tables (GdprContext only)
  /**
   * getContextJoinQuery.
   *
   * @param field a {@link java.lang.reflect.Field} object
   * @param properties a {@link java.util.List} object
   * @return a {@link org.damap.base.rest.gdpr.domain.GdprQuery} object
   * @throws org.damap.base.rest.gdpr.exceptions.NoSuchContextPropertyException if any.
   */
  public GdprQuery getContextJoinQuery(Field field, List<String> properties)
      throws NoSuchContextPropertyException {

    GdprQuery query = new GdprQuery();
    query.setFieldName(field.getName());
    Class<?> clazz = field.getType();
    query.setRoot(clazz);
    query.setBase(new ArrayList<>());

    // List for simple properties
    List<String> simpleProperties = new ArrayList<>();
    Map<String, List<String>> nestedProperties = new HashMap<>();

    for (String property : properties) {
      String[] propertyParts = property.split("\\.", 2);
      if (propertyParts.length > 1) {
        if (nestedProperties.containsKey(propertyParts[0])) {
          nestedProperties.get(propertyParts[0]).add(propertyParts[1]);
        } else {
          List<String> nestedProperty = new ArrayList<>();
          nestedProperty.add(propertyParts[1]);
          nestedProperties.put(propertyParts[0], nestedProperty);
        }
      } else {
        // Add simple property
        simpleProperties.add(property);
      }
    }

    // Add simple properties to query
    for (String property : simpleProperties) {
      Field f = getField(clazz, property);
      if (isPrimitiveOrEnum(f.getType())) {
        query.getBase().add(getColumnName(f));
      }
    }

    // Add nested properties to query
    query.setContextJoins(new ArrayList<>());
    for (Map.Entry<String, List<String>> entry : nestedProperties.entrySet()) {
      Field f = getField(clazz, entry.getKey());
      query.getContextJoins().add(getContextJoinQuery(f, entry.getValue()));
    }

    return query;
  }

  private Field getField(Class<?> clazz, String fieldName) throws NoSuchContextPropertyException {
    try {
      if (Objects.equals(fieldName, "id")) {
        // ID is defined by superclass, getDeclaredField() doesn't work here
        return clazz.getField(fieldName);
      } else {
        return clazz.getDeclaredField(fieldName);
      }
    } catch (NoSuchFieldException e) {
      throw new NoSuchContextPropertyException(clazz, fieldName);
    }
  }

  private String getColumnName(Field field) {
    return field.getName();
  }

  private boolean isPrimitiveOrEnum(@NotNull Class<?> clazz) {
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
