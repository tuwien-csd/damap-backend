package org.damap.base.rest.gdpr.exceptions;

/** NoSuchContextPropertyException class. */
public class NoSuchContextPropertyException extends RuntimeException {

  /**
   * Constructor for NoSuchContextPropertyException.
   *
   * @param clazz a {@link java.lang.Class} object
   * @param propertyName a {@link java.lang.String} object
   */
  public NoSuchContextPropertyException(Class<?> clazz, String propertyName) {
    super(
        String.format(
            "@GdprContext property %s is not declared in %s.",
            propertyName, clazz.getSimpleName()));
  }

  /**
   * Constructor for NoSuchContextPropertyException.
   *
   * @param message a {@link java.lang.String} object
   */
  public NoSuchContextPropertyException(String message) {
    super(message);
  }

  /** Constructor for NoSuchContextPropertyException. */
  public NoSuchContextPropertyException() {
    super("@GdprContext property is not available.");
  }
}
