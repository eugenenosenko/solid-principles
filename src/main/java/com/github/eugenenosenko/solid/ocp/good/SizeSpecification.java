package com.github.eugenenosenko.solid.ocp.good;

import com.github.eugenenosenko.solid.ocp.bad.Product;
import com.github.eugenenosenko.solid.ocp.bad.Size;

public class SizeSpecification implements Specification<Product> {
  private final Size size;

  public SizeSpecification(Size size) {
    this.size = size;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.size == size;
  }
}
