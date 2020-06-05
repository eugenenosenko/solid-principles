package com.github.eugenenosenko.solid.ocp.good;

import com.github.eugenenosenko.solid.ocp.bad.Product;

import java.util.List;
import java.util.stream.Stream;

public class BetterFilter implements Filter<Product> {

  @Override
  public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
    return items.stream().filter(spec::isSatisfied);
  }
}
