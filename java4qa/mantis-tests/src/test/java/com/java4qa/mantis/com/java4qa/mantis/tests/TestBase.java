package com.java4qa.mantis.com.java4qa.mantis.tests;


import com.java4qa.mantis.com.java4qa.mantis.appmanager.ApplicationManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;

public class TestBase {

  protected static final ApplicationManager app
      = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
//    app.ssh().upload(new File("src/test/resources/config_inc.php"), "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php", "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
//     app.ssh().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

}