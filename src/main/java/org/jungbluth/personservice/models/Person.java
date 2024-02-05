package org.jungbluth.personservice.models;

import java.sql.Date;

public class Person {
  private String name;
  private String firstName;
  private Date dateOfBirth;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Person(String name, String firstName, Date dateOfBirth) {
    this.name = name;
    this.firstName = firstName;
    this.dateOfBirth = dateOfBirth;
  }
}
