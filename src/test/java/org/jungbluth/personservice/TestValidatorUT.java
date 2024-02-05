package org.jungbluth.personservice;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Date;

import org.jungbluth.personservice.models.ValidationResult;
import org.jungbluth.personservice.services.PersonValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestValidatorUT {

  private PersonValidatorService service;

  @BeforeEach
  public void setup() {
    service = new PersonValidatorService();
  }

  @Test
  public void testSurNameValidationNameTooShort() {
    String name = "Hi";
    ValidationResult result = service.validateSurName(name);
    assertFalse(result.isValid, "Name was too short but validation passed");
  }

  @Test
  public void testSurNameValidationNameTooLong() {
    String name = "This name is too long and should not pass validation";
    ValidationResult result = service.validateSurName(name);
    assertFalse(result.isValid, "Name was too long but validation passed");
  }

  @Test
  public void testFirstNameValidationNameTooLong() {
    String name = "This name is too long and should not pass validation";
    ValidationResult result = service.validateFirstName(name);
    assertFalse(result.isValid, "Name was too long but validation passed");
  }

  @Test
  public void testDateOfBirthValidationDateIsInFuture() {
    Date date = new Date(System.currentTimeMillis() + 1000000);
    ValidationResult result = service.validateDateOfBirth(date);
    assertFalse(result.isValid, "Date was in future but passed validation");
  }

}
