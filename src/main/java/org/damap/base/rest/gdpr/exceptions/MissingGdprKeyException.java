package org.damap.base.rest.gdpr.exceptions;

/** MissingGdprKeyException class. */
public class MissingGdprKeyException extends RuntimeException {

  /**
   * Constructor for MissingGdprKeyException.
   *
   * @param message a {@link java.lang.String} object
   */
  public MissingGdprKeyException(String message) {
    super(message);
  }

  /** Constructor for MissingGdprKeyException. */
  public MissingGdprKeyException() {
    super("Entity is missing a @GdprKey.");
  }
}
