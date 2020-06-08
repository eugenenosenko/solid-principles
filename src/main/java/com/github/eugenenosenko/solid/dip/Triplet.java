package com.github.eugenenosenko.solid.dip;

public final class Triplet<F, S, T> {
  private final F value0;
  private final S value1;
  private final T value2;

  public Triplet(F value0, S value1, T value2) {
    this.value0 = value0;
    this.value1 = value1;
    this.value2 = value2;
  }

  public F getValue0() {
    return value0;
  }

  public S getValue1() {
    return value1;
  }

  public T getValue2() {
    return value2;
  }
}
