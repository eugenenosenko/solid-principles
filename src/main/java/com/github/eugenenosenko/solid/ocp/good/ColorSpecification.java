package com.github.eugenenosenko.solid.ocp.good;

import com.github.eugenenosenko.solid.ocp.bad.Color;
import com.github.eugenenosenko.solid.ocp.bad.Product;

public class ColorSpecification implements Specification<Product> {
  private final Color color;

  public ColorSpecification(Color color) {
    this.color = color;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.color == color;
  }
}
