package com.example.helloworld.db;

import com.example.helloworld.core.Person;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class PersonDAO extends AbstractDAO<Person> {
  public PersonDAO(SessionFactory factory) {
    super(factory);
  }

  public Optional<Person> findById(Long id) {
    return Optional.ofNullable(get(id));
  }

  public Person create(Person person) {
    return persist(person);
  }

  public List<Person> findAll() {
    return list((Query<Person>) namedQuery("com.example.helloworld.core.Person.findAll"));
  }

  public List<Person> find(List<String> fullName) {
    Query q = namedQuery("com.example.helloworld.core.Person.find");
    q.setParameter("fullName", fullName);
    // return (List<Person>) namedQuery("com.example.helloworld.core.Person.find");
    // System.out.println(q);
    return list((Query<Person>) q);
  }
}
