package com.github.eugenenosenko.solid.dip.good;

import com.github.eugenenosenko.solid.dip.Person;

import java.util.List;

public interface RelationshipBrowser {
  List<Person> findAllChildrenOf(String name);
}
