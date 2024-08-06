package org.damap.base.annotations.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class that holds information that is subject to the EU General Data Protection
 * Regulation. {@code @Gdpr} is meant to be used in conjunction with {@link
 * org.damap.base.annotations.gdpr.GdprKey}, {@link org.damap.base.annotations.gdpr.GdprContext},
 * {@link org.damap.base.annotations.gdpr.GdprBase} and {@link
 * org.damap.base.annotations.gdpr.GdprExtended}, which mark all relevant fields in the class.
 *
 * <p>A class annotated with {@code @Gdpr} must also contain exactly one field annotated with {@link
 * GdprKey}.
 *
 * <p>Usage:
 *
 * <blockquote>
 *
 * <pre>
 * &#064;Gdpr
 * public class MyEntity {
 *
 *     &#064;GdprKey
 *     String personId;
 *
 *     &#064;GdprContext
 *     String context;
 *
 *     &#064;GdprContext(properties = {"name", "project.title", "project.leader"})
 *     OtherEntity info;
 *
 *     &#064;GdprBase
 *     String name;
 *
 *     &#064;GdprExtended
 *     String role;
 * }
 * </pre>
 *
 * </blockquote>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gdpr {}
