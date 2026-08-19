package org.damap.base.rda.dmpcommonstandard;

public class CommonStandardCompatibilityException extends RuntimeException {
  public CommonStandardCompatibilityException(String message) {
    super(
        "Cannot convert DMP to DAMAP format: "
            + message
            + " (Note: DAMAP is not fully compliant with the Common Standard and cannot import all Common Standard objects correctly.");
  }
}
