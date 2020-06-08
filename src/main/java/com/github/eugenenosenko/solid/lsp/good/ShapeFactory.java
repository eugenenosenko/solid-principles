package com.github.eugenenosenko.solid.lsp.good;

import com.github.eugenenosenko.solid.lsp.Rectangle;

public class ShapeFactory {
  // no need for a Square class at all
  // creation of squares can be delegated to a factory
  public static Rectangle createSquare(int side) {
    Rectangle rect = new Rectangle();
    rect.setWidth(side);
    rect.setHeight(side);
    return rect;
  }
}
