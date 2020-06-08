package com.github.eugenenosenko.solid.isp.good;

public class ScanningPrinter implements Printer, Scanner {
  @Override
  public void print(String text) {
    System.out.println("Printing " + text);
  }

  @Override
  public void scan() {
    System.out.println("Scanning");
  }
}
