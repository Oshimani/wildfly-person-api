package org.jungbluth.personservice.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jungbluth.personservice.models.Person;
import org.jungbluth.personservice.services.PersonDbService;

@Path("/")
public class PersonEndpoint {
  @Inject
  private PersonDbService personService;

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(Person person) {
    // validate
    if (person == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Person is required").build();
    }
    if (person.getSurName().length() <= 3) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Surname must be longer than 3 characters").build();
    }
    if (person.getFirstName().length() >= 20) {
      return Response.status(Response.Status.BAD_REQUEST).entity("First name must be shorter than 20 characters")
          .build();
    }
    if (person.getDateOfBirth().after(new java.util.Date())) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Date of birth must be in the past").build();
    }

    // create person
    try {
      personService.createPerson(person);
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }

    // return response
    return Response.created(null).build();
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPersons() {
    try {
      Person person = personService.getPerson("Jannick", "Jungbluth");
      return Response.ok(person).build();
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }
}
