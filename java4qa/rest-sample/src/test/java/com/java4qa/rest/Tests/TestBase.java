package com.java4qa.rest.Tests;


import com.java4qa.rest.appmanager.ApplicationManager;
import org.testng.annotations.BeforeSuite;

public class TestBase {

  protected static final ApplicationManager app
      = new ApplicationManager();

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
//    app.ssh().upload(new File("src/test/resources/config_inc.php"), "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php", "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php.bak");
  }
}