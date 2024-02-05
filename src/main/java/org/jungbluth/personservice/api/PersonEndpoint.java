package org.jungbluth.personservice.api;

import jakarta.inject.Inject;
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
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(final Person person){
    // validate


    // create person
    personService.createPerson(person)

    // return response
    return Response.created(null).build();
  }
}
