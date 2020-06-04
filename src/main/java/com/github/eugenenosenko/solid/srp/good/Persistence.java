package com.github.eugenenosenko.solid.srp.good;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

// handles the responsibility of persisting objects
public class Persistence<T> {
  public void saveToFile(T t, String filename, boolean overwrite) throws Exception {
    if (overwrite || new File(filename).exists())
      try (PrintStream out = new PrintStream(filename)) {
        out.println(t.toString());
      }
  }

  public void load(T t, String filename) {}

  public void load(T t, URL url) {}
}
