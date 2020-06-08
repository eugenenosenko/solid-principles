package com.github.eugenenosenko.solid.dip;

public class Person {
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Person(String name) {
    this.name = name;
  }
}
