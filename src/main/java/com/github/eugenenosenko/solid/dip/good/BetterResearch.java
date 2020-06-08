package com.github.eugenenosenko.solid.dip.good;

import com.github.eugenenosenko.solid.dip.Person;

import java.util.List;

public class BetterResearch {
  public BetterResearch(RelationshipBrowser browser) {
    List<Person> children = browser.findAllChildrenOf("John");
    for (Person child : children) System.out.println("John has a child called " + child.name);
  }
}
