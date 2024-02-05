package org.jungbluth.personservice.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Properties;

import org.jungbluth.personservice.models.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonDbService {

  private Connection conn;

  public PersonDbService() {
    conn = connect();
  }

  private Connection connect() {
    Connection conn = null;
    Properties connectionProps = new Properties();
    // TODO: move credentials to environment variable
    connectionProps.put("user", "postgres");
    connectionProps.put("password", "example");
    try {
      conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/persons", connectionProps);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return conn;
  }

  public void createPerson(Person person) throws SQLException {
    try {
      PreparedStatement statement = conn
          .prepareStatement("INSERT INTO persons (first_name, sur_name, date_of_birth) VALUES (?, ?, ?)");
      statement.setString(1, person.getFirstName());
      statement.setString(2, person.getSurName());
      statement.setDate(3, person.getDateOfBirth());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw e;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public Person getPerson(String firstName, String surName) throws SQLException {
    try {
      PreparedStatement statement = conn
          .prepareStatement("SELECT * FROM persons WHERE first_name = ? AND sur_name = ? LIMIT 1");
      statement.setString(1, firstName);
      statement.setString(2, surName);

      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        // no user found with this name
        return null;
      } else {

        String fn = resultSet.getString("first_name");
        String sn = resultSet.getString("sur_name");
        Date dob = resultSet.getDate("date_of_birth");
        return new Person(sn, fn, dob);
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw e;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }
}
