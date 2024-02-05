package org.jungbluth.personservice.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.jungbluth.personservice.models.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonDbService {

  private Connection conn;

  public PersonDbService() throws Exception {
    try {
      conn = connect();
    } catch (Exception e) {
      throw e;
    }
  }

  private Connection connect() throws RuntimeException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    // TODO: move credentials to environment variable
    connectionProps.put("user", "postgres");
    connectionProps.put("password", "jungbluth");
    try {
      conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/persons", connectionProps);
    } catch (SQLException e) {
      throw new RuntimeException("Could not connect to database");
    }
    return conn;
  }

  public Person createPerson(Person person) throws SQLException {
    try {
      PreparedStatement statement = conn
          .prepareStatement("INSERT INTO persons (first_name, sur_name, date_of_birth) VALUES (?, ?, ?)");
      statement.setString(1, person.getFirstName());
      statement.setString(2, person.getSurName());
      statement.setDate(3, person.getDateOfBirth());
      statement.execute();
      return person;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw e;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public Person updatePerson(int id, Person person) throws SQLException {
    try {

      // get person data that is not provided in the request
      Person dbPerson = getPerson(id);
      if (dbPerson == null) {
        throw new SQLException("Person not found");
      }

      PreparedStatement statement = conn
          .prepareStatement(
              "UPDATE persons SET first_name = ?, sur_name = ?, date_of_birth = ? WHERE id = ? RETURNING *");
      // keep old data if not provided in the update request
      statement.setString(1, person.getFirstName() == null ? dbPerson.getFirstName() : person.getFirstName());
      statement.setString(2, person.getSurName() == null ? dbPerson.getSurName() : person.getSurName());
      statement.setDate(3, person.getDateOfBirth() == null ? dbPerson.getDateOfBirth() : person.getDateOfBirth());
      statement.setInt(4, id);

      statement.execute();

      // return the updated person
      ResultSet resSet = statement.getResultSet();
      if (resSet.next()) {
        return new Person(
            resSet.getInt("id"),
            resSet.getString("sur_name"),
            resSet.getString("first_name"),
            resSet.getDate("date_of_birth"));
      } else {
        // this should never happen
        throw new SQLException("Person not found");
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public void deletePerson(int id) throws SQLException {
    try {
      PreparedStatement statement = conn
          .prepareStatement("DELETE FROM persons WHERE id = ?");
      statement.setInt(1, id);
      statement.execute();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public Person getPerson(int id) throws SQLException {
    try {
      PreparedStatement statement = conn
          .prepareStatement("SELECT * FROM persons WHERE id = ?");
      statement.setInt(1, id);

      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        // no user found with this name
        return null;
      } else {
        // return user
        return new Person(
            resultSet.getInt("id"),
            resultSet.getString("sur_name"),
            resultSet.getString("first_name"),
            resultSet.getDate("date_of_birth"));
      }

    } catch (SQLException e) {
      // throw sql exception
      System.err.println(e.getMessage());
      throw e;
    } catch (Exception e) {
      // throw unknown exception
      System.err.println(e.getMessage());
      throw e;
    }
  }
}
