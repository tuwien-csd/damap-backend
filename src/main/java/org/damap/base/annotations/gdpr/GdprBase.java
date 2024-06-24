package org.damap.base.annotations.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field in an entity as base information for EU General Data Protection Regulation compliance.
 * <p>
 * {@code @GdprBase} mark fields containing personal data.
 * <p>
 * For usage instructions, see {@link Gdpr}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GdprBase {
}
