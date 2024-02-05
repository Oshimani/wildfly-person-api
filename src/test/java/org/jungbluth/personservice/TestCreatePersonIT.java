package org.jungbluth.personservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jungbluth.personservice.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Entity;

@RunAsClient
@ExtendWith(ArquillianExtension.class)
public class TestCreatePersonIT {

  @Test
  public void testCreatePersonEndpoint() {

    Person person = new Person(
        "Jannick",
        "Jungbluth",
        Date.valueOf("1994-06-12"));
    // send empty post request
    try (Client client = ClientBuilder.newClient()) {
      Response response = client
          .target("http://localhost:8080/")
          .path("/person")
          .request()
          .header("Content-Type", "application/json")
          .post(Entity.json(person));

      assertEquals(200, response.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
