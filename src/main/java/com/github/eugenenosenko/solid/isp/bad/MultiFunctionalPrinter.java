package com.github.eugenenosenko.solid.isp.bad;

public class MultiFunctionalPrinter implements Printer {

  @Override
  public void print(String text) {
    System.out.println("Printing" + " " + text);
  }

  @Override
  public void scan() {
    System.out.println("Scanning...");
  }

  @Override
  public void fax() {
    System.out.println("Faxing...");
  }
}
