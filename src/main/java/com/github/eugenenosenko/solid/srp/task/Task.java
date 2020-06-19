package com.github.eugenenosenko.solid.srp.task;

public class Task {
  // Refactor the code below so that it does not violate SRP

}

class Page {}

class WebElement {}

class PageNavigationController {
  void navigateTo(Page page) {
    System.out.println("Navigating to page " + page);
  }

  void refresh() {
    System.out.println("Refreshing the page " + currentPage());
  }

  void back() {}

  void forward() {}

  void navigateTo(String pageName) {
    System.out.println("Navigating to " + pageName);
  }

  Page currentPage() {
    System.out.println("Getting current page");
    return new Page();
  }

  void close(Page page) {
    System.out.println("Closing page " + page);
  }

  void scrollToElement(WebElement element) {
    System.out.println("Scrolling to element " + element);
  }

  boolean isElementPresentOnPage(Page page, WebElement element) {
    System.out.println("Checking if " + element + " element is present on " + page);
    return false;
  }

  boolean isElementPresentOnCurrentPage(WebElement element) {
    return isElementPresentOnPage(currentPage(), element);
  }
}
