package org.jungbluth.personservice.services;

public class PersonValidatorService {
  public boolean validateName(String name) {
    // test if name contains non alphanumeric characters
    return name.matches("^[a-zA-Z]*$");
  }
}
