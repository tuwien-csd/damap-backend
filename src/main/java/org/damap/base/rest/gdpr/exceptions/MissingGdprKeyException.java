package org.damap.base.rest.gdpr.exceptions;

public class MissingGdprKeyException extends RuntimeException {

  public MissingGdprKeyException(String message) {
    super(message);
  }

  public MissingGdprKeyException() {
    super("Entity is missing a @GdprKey.");
  }
}
