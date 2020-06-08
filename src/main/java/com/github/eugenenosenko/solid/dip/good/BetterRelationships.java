package com.github.eugenenosenko.solid.dip.good;

import com.github.eugenenosenko.solid.dip.Person;
import com.github.eugenenosenko.solid.dip.Relationship;
import com.github.eugenenosenko.solid.dip.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BetterRelationships implements RelationshipBrowser {
  private final List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

  public void addParentAndChild(Person parent, Person child) {
    relations.add(new Triplet<>(parent, Relationship.PARENT, child));
    relations.add(new Triplet<>(child, Relationship.CHILD, parent));
  }

  @Override
  public List<Person> findAllChildrenOf(String name) {
    return relations.stream()
        .filter(
            x -> Objects.equals(x.getValue0().name, name) && x.getValue1() == Relationship.PARENT)
        .map(Triplet::getValue2)
        .collect(Collectors.toList());
  }
}
