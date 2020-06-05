package com.github.eugenenosenko.solid.ocp.good;

interface Specification<T> {
  boolean isSatisfied(T item);
}
