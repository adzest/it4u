package com.java4qa.mantis.com.java4qa.mantis.tests;

import com.java4qa.mantis.com.java4qa.mantis.appmanager.HttpSession;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    HttpSession session = app.newSession();
    assertTrue(session.login(app.getProperty("mantis.admin"), app.getProperty("mantis.password")));
    assertTrue(session.isLoggedInAs(app.getProperty("mantis.admin")));
  }
}
