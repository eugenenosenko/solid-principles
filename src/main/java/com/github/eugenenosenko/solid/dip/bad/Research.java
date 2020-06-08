package com.github.eugenenosenko.solid.dip.bad;

import com.github.eugenenosenko.solid.dip.Person;
import com.github.eugenenosenko.solid.dip.Relationship;
import com.github.eugenenosenko.solid.dip.Triplet;

import java.util.List;

public class Research {

  public Research(Relationships relationships) {

    // high-level. business logic related
    List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
    relations.stream()
        .filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
        .forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
  }
}
