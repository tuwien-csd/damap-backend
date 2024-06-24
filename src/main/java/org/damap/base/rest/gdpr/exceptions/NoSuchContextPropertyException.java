package org.damap.base.rest.gdpr.exceptions;

public class NoSuchContextPropertyException extends RuntimeException {

    public NoSuchContextPropertyException(Class<?> clazz, String propertyName) {
        super(String.format("@GdprContext property %s is not declared in %s.", propertyName, clazz.getSimpleName()));
    }

    public NoSuchContextPropertyException(String message) {
        super(message);
    }

    public NoSuchContextPropertyException() {
        super("@GdprContext property is not available.");
    }
}
