package org.damap.base.annotations.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field in an entity as context information for EU General Data Protection Regulation
 * compliance.
 *
 * <p>Fields marked with {@code @GdprContext} are NOT personal data, but add context information to
 * GDPR data. E.g. DMP titles etc. are not personal data, but can be useful to understand when a
 * user is listed as contributor.
 *
 * <p>If the type of a field annotated with {@code @GdprContext} is not primitive, {@code
 * properties} shall be used to specify which properties of the entity shall be retrieved. You may
 * specify multiple (primitive) properties separated by comma and specify nested properties by using
 * dots (like {@code "name, address.street, address.city"}).
 *
 * <p><i>Side note: Queries for nested properties are built using recursion, keep this in mind when
 * using this annotation.</i>
 *
 * <p>For more usage instructions, see {@link Gdpr}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GdprContext {

  String[] properties() default {};
}
