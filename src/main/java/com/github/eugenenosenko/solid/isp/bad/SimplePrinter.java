package com.github.eugenenosenko.solid.isp.bad;

public class SimplePrinter implements Printer {
  @Override
  public void print(String text) {
    System.out.println("Printing: " + text);
  }

  @Override
  public void scan() {
    // no implementation
  }

  @Override
  public void fax() {
    // no implementation
  }
}
