package org.jungbluth.personservice.services;

import org.jungbluth.personservice.models.Person;
import org.jungbluth.personservice.models.ValidationResult;

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Date;

@ApplicationScoped
public class PersonValidatorService {

  public PersonValidatorService() {
  }

  public ValidationResult validateSurName(String surName) {
    if (surName == null) {
      return new ValidationResult(false, "Surname must not be empty");
    }

    if (surName.length() < 3) {
      return new ValidationResult(false, "Surname must be at least 3 characters long");
    }

    if (surName.length() > 20) {
      return new ValidationResult(false, "Surname must be shorter than 20 characters");
    }

    return new ValidationResult(true, null);
  }

  public ValidationResult validateFirstName(String firstName) {

    if (firstName.length() > 20) {
      return new ValidationResult(false, "First name must be shorter than 20 characters");
    }

    return new ValidationResult(true, null);
  }

  public ValidationResult validateDateOfBirth(Date dateOfBirth) {
    if (dateOfBirth.after(new java.util.Date())) {
      return new ValidationResult(false, "Date of birth must be in the past");
    }

    return new ValidationResult(true, null);
  }

  public ValidationResult[] validatePerson(Person person) {
    ValidationResult[] results = new ValidationResult[3];
    results[0] = validateSurName(person.getSurName());
    results[1] = validateFirstName(person.getFirstName());
    results[2] = validateDateOfBirth(person.getDateOfBirth());
    return results;
  }
}
