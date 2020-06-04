package com.github.eugenenosenko.solid.srp;

import com.github.eugenenosenko.solid.srp.good.Journal;
import com.github.eugenenosenko.solid.srp.good.Persistence;

public class Demo {
  public static void main(String[] args) throws Exception {

    Journal j = new Journal();

    j.addEntry("I cried today");
    j.addEntry("I ate a bug");

    System.out.println(j);

    Persistence<Journal> p = new Persistence<>();
    String filename = "journal.txt";
    p.saveToFile(j, filename, true);
  }
}
