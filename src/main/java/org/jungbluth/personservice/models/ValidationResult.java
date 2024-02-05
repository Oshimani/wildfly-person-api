package org.jungbluth.personservice.models;

public class ValidationResult {
  public boolean isValid;
  public String message;

  public ValidationResult() {
  }

  public ValidationResult(boolean isValid, String message) {
    this.isValid = isValid;
    this.message = message;
  }
}