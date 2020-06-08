package com.github.eugenenosenko.solid.dip;

import com.github.eugenenosenko.solid.dip.bad.Research;
import com.github.eugenenosenko.solid.dip.bad.Relationships;
import com.github.eugenenosenko.solid.dip.good.BetterRelationships;
import com.github.eugenenosenko.solid.dip.good.BetterResearch;
import com.github.eugenenosenko.solid.dip.good.RelationshipBrowser;

public class Demo {
  public static void main(String[] args) {
    Person parent = new Person("John");
    Person child1 = new Person("Chris");
    Person child2 = new Person("Matt");

    // low-level module
    Relationships relationships = new Relationships();
    relationships.addParentAndChild(parent, child1);
    relationships.addParentAndChild(parent, child2);

    // higher module depends on low level module
    new Research(relationships);

    // abstraction
    RelationshipBrowser relationshipBrowser = new BetterRelationships();
    // higher module depends on abstraction
    new BetterResearch(relationshipBrowser);
  }
}
