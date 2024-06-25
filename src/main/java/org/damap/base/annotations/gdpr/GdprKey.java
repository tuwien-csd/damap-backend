package org.damap.base.annotations.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field in a class as a key column for EU General Data Protection Regulation data requests.
 * This annotation is meant to be used in conjunction with {@link Gdpr}.
 *
 * <p>For usage instructions, see {@link Gdpr}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GdprKey {}
