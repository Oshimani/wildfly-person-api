package org.jungbluth.personservice.models;

import java.sql.Date;
import jakarta.json.bind.annotation.JsonbDateFormat;

public class Person {
  private String surName;
  private String firstName;
  @JsonbDateFormat("yyyy-MM-dd")
  private Date dateOfBirth;

  public Person() {
  }

  public Person(String surName, String firstName, Date dateOfBirth) {
    this.surName = surName;
    this.firstName = firstName;
    this.dateOfBirth = dateOfBirth;
  }

  public String getSurName() {
    return surName;
  }

  public void setSurName(String surName) {
    this.surName = surName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
