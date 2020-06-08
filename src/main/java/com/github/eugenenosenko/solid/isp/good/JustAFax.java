package com.github.eugenenosenko.solid.isp.good;

public class JustAFax implements Fax {

  @Override
  public void fax() {
    System.out.println("Faxing");
  }
}
