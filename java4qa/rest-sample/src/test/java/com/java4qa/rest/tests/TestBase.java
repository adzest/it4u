package com.java4qa.rest.tests;


import com.java4qa.rest.appmanager.ApplicationManager;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {

  protected static final ApplicationManager app
      = new ApplicationManager();

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
//    app.ssh().upload(new File("src/test/resources/config_inc.php"), "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php", "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php.bak");
  }
}