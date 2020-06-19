package com.github.eugenenosenko.solid.ocp;

import com.github.eugenenosenko.solid.ocp.bad.Color;
import com.github.eugenenosenko.solid.ocp.bad.Product;
import com.github.eugenenosenko.solid.ocp.bad.ProductFilter;
import com.github.eugenenosenko.solid.ocp.bad.Size;
import com.github.eugenenosenko.solid.ocp.good.AndSpecification;
import com.github.eugenenosenko.solid.ocp.good.BetterFilter;
import com.github.eugenenosenko.solid.ocp.good.ColorSpecification;
import com.github.eugenenosenko.solid.ocp.good.SizeSpecification;

import java.util.Arrays;
import java.util.List;

public class Demo {
  public static void main(String[] args) {
    Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
    Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
    Product house = new Product("House", Color.BLUE, Size.LARGE);

    // old implementation
    List<Product> products = Arrays.asList(apple, tree, house);
    ProductFilter pf = new ProductFilter();
    pf.filterByColor(products, Color.GREEN)
        .forEach(p -> System.out.println(" - " + p.name + " is green"));

    // new implementation
    BetterFilter bf = new BetterFilter();
    System.out.println("Green products (new):");
    bf.filter(products, new ColorSpecification(Color.GREEN))
        .forEach(p -> System.out.println(" - " + p.name + " is green"));

    System.out.println("Large products:");
    bf.filter(products, new SizeSpecification(Size.LARGE))
        .forEach(p -> System.out.println(" - " + p.name + " is large"));

    System.out.println("Large blue items:");
    bf.filter(
            products,
            new AndSpecification<>(
                new ColorSpecification(Color.BLUE), new SizeSpecification(Size.LARGE)))
        .forEach(p -> System.out.println(" - " + p.name + " is large and blue"));
  }
}
