package org.jungbluth.personservice.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jungbluth.personservice.models.Person;
import org.jungbluth.personservice.models.ValidationResult;
import org.jungbluth.personservice.services.PersonDbService;
import org.jungbluth.personservice.services.PersonValidatorService;

@Path("/")
public class PersonEndpoint {
  @Inject
  private PersonDbService personService;
  @Inject
  private PersonValidatorService personValidatorService;

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(Person person) {
    // validate
    if (person == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Person must not be null").build();
    }

    ValidationResult[] results = personValidatorService.validatePerson(person);
    for (ValidationResult result : results) {
      if (!result.isValid) {
        return Response.status(Response.Status.BAD_REQUEST).entity(result.message).build();
      }
    }

    try {
      // create person
      Person dbPerson = personService.createPerson(person);
      // return response
      return Response.ok(dbPerson).build();
    } catch (RuntimeException e) {
      return Response.serverError().entity("Database unavailable").build();
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePerson(@PathParam("id") int id, Person person) {
    // validate all props that were given
    if (person == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Person must not be null").build();
    }
    if (person.getSurName() != null) {
      ValidationResult result = personValidatorService.validateSurName(person.getSurName());
      if (!result.isValid) {
        return Response.status(Response.Status.BAD_REQUEST).entity(result.message).build();
      }
    }
    if (person.getFirstName() != null) {
      ValidationResult result = personValidatorService.validateFirstName(person.getFirstName());
      if (!result.isValid) {
        return Response.status(Response.Status.BAD_REQUEST).entity(result.message).build();
      }
    }
    if (person.getDateOfBirth() != null) {
      ValidationResult result = personValidatorService.validateDateOfBirth(person.getDateOfBirth());
      if (!result.isValid) {
        return Response.status(Response.Status.BAD_REQUEST).entity(result.message).build();
      }
    }

    try {
      // update person
      Person updatedPerson = personService.updatePerson(id, person);
      return Response.ok(updatedPerson).build();
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @DELETE
  @Path("/{id}")
  public Response deletePerson(@PathParam("id") int id) {
    try {
      personService.deletePerson(id);
      return Response.ok().build();
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPersons(@PathParam("id") int id) {
    try {
      Person person = personService.getPerson(id);
      return Response.ok(person).build();
    } catch (Exception e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }
}
