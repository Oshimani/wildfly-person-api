package org.jungbluth.personservice.services;

import java.sql.Connection;
import java.util.Properties;

import org.jungbluth.personservice.models.Person;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.Resource;

public class PersonDbService {

  @Resource(lookup = "java:/jboss/datasources/PersonDS")

  private Connection connect() {
    Connection conn = null;
    Properties connectionProps = new Properties();

  }

  public void createPerson(Person person) {
    return;
  }
}
