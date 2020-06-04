package com.github.eugenenosenko.solid.ocp.good;

import com.github.eugenenosenko.solid.ocp.bad.Color;
import com.github.eugenenosenko.solid.ocp.bad.Product;
import com.github.eugenenosenko.solid.ocp.bad.Size;

import java.util.List;
import java.util.stream.Stream;

interface Specification<T> {
  boolean isSatisfied(T item);
}

interface Filter<T> {
  Stream<T> filter(List<T> items, Specification<T> spec);
}

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

public class AndSpecification<T> implements Specification<T> {
  private final Specification<T> first, second;

  public AndSpecification(Specification<T> first, Specification<T> second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean isSatisfied(T item) {
    return first.isSatisfied(item) && second.isSatisfied(item);
  }
}

public class BetterFilter implements Filter<Product> {

  @Override
  public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
    return items.stream().filter(spec::isSatisfied);
  }
}
