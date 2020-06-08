package com.github.eugenenosenko.solid.lsp.bad;

import com.github.eugenenosenko.solid.lsp.Rectangle;

public class Square extends Rectangle {
  public Square() {}

  public Square(int size) {
    width = height = size;
  }

  @Override
  public void setWidth(int width) {
    super.setWidth(width);
    // lsp violation
    super.setHeight(width);
  }

  @Override
  public void setHeight(int height) {
    super.setHeight(height);
    // lsp violation
    super.setWidth(height);
  }
}
