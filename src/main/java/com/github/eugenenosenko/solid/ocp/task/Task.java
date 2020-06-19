package com.github.eugenenosenko.solid.ocp.task;

public class Task {
  // Refactor the code below so that it does follows OCP principle
}

class Calculator {

  int calculate(String action, int a, int b) {
    if (action.equals("add")) {
      return a + b;
    } else if (action.equals("subtract")) {
      return a - b;
    } else if (action.equals("divide")) {
      return a / b;
    } else if (action.equals("multiple")) {
      return a * b;
    } else {
      throw new UnsupportedOperationException(
          "Do not support other operations except for add / subtract / divide / multiply");
    }
  }
}
