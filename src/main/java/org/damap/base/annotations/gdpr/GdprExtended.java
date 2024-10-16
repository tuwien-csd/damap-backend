package org.damap.base.annotations.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field in an entity as extended information for EU General Data Protection Regulation
 * compliance.
 *
 * <p>Fields marked with {@code @GdprExtended} may not be considered personal data, but could be
 * used in a specific context and/or with additional information to identify an individual.
 * {@code @GdprExtended} is also used to add more information as to why personal data is stored.
 *
 * <p>If the type of a field marked with {@code @GdprExtended} is not primitive (e.g. Integer,
 * String etc.), all primitive fields of that type are considered extended information.
 *
 * <p>For usage instructions, see {@link org.damap.base.annotations.gdpr.Gdpr}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GdprExtended {}
