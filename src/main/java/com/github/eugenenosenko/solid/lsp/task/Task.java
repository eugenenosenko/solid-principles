package com.github.eugenenosenko.solid.lsp.task;

public class Task {
  // Refactor code below so that it doesn't violate LSP
}

abstract class Animal {
  abstract boolean isWarmBlooded();
}

class Bird extends Animal {

  void fly() {
    System.out.println("I'm flying ");
  }

  @Override
  final boolean isWarmBlooded() {
    return true;
  }
}

class Ostrich extends Bird {

  void hideHeadInTheSand() {
    System.out.println("Hiding...");
  }
}

class Duck extends Bird {

  void quack() {
    System.out.println("I'm hungry..");
  }
}
