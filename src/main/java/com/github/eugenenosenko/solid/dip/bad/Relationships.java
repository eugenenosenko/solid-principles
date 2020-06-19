package com.github.eugenenosenko.solid.dip.bad;

import com.github.eugenenosenko.solid.dip.Person;
import com.github.eugenenosenko.solid.dip.Relationship;
import com.github.eugenenosenko.solid.dip.Triplet;

import java.util.ArrayList;
import java.util.List;

public class Relationships {
  private final List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

  // D.I.P violated. exposing internal storage implementation
  public List<Triplet<Person, Relationship, Person>> getRelations() {
    return relations;
  }

  public void addParentAndChild(Person parent, Person child) {
    relations.add(new Triplet<>(parent, Relationship.PARENT, child));
    relations.add(new Triplet<>(child, Relationship.CHILD, parent));
  }
}
