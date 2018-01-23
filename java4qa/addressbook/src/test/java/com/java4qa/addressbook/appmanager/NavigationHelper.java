package com.java4qa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))
          && wd.findElement(By.tagName("h1")).getText().equals("Groups")
          && isElementPresent(By.name("new"))) {
      return;
    }
    click(By.cssSelector("#nav > ul > li:nth-child(3) > a"));
  }

  public void contactCreationPage() {
    click(By.cssSelector("#nav > ul > li:nth-child(2) > a"));
  }

  public void homePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.cssSelector("#nav > ul > li:nth-child(1) > a"));
  }

}
