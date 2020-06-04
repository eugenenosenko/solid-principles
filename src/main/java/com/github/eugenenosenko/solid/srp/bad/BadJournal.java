package com.github.eugenenosenko.solid.srp.bad;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BadJournal {
  private final List<String> entries = new ArrayList<>();
  private static int count = 0;

  void addEntry(String text) {
    entries.add("" + (++count) + ": " + text);
  }

  void removeEntry(int index) {
    entries.remove(index);
  }

  // SRP is broken
  public void save(String filename) throws Exception {
    try (PrintStream out = new PrintStream(filename)) {
      out.println(toString());
    }
  }

  // SRP is broken
  public void load(String filename) {}

  // SRP is broken
  public void load(URL url) {}
}
