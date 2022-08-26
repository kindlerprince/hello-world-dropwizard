package com.example.helloworld.resources;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.jetbrains.annotations.NotNull;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {
  private final PersonDAO peopleDAO;

  public PeopleResource(PersonDAO PeopleDAO) {
    this.peopleDAO = PeopleDAO;
  }

  @POST
  @UnitOfWork
  public Person createPerson(@Valid Person person) {
    return peopleDAO.create(person);
  }

  @GET
  @UnitOfWork
  public Optional<Person> findById(@QueryParam("id") @NotNull Optional<Long> id) {
    return peopleDAO.findById(id.orElse(new Long(1)));
  }

  @GET
  @Path("/all")
  @UnitOfWork
  public List<Person> listPeople() {
    return peopleDAO.findAll();
  }

  @GET
  @Path("/find")
  @UnitOfWork
  public List<Person> findPeople() {
    List<String> fullname = Arrays.asList("Amit", "Prince Kumar");
    return peopleDAO.find(fullname);
  }
}
