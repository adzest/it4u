package com.java4qa.mantis.com.java4qa.mantis.tests;

import com.java4qa.mantis.com.java4qa.mantis.appmanager.HttpSession;
import com.java4qa.mantis.com.java4qa.mantis.model.Issue;
import com.java4qa.mantis.com.java4qa.mantis.model.Project;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WithIntegrationsTests extends TestBase{

  @Test
  public void testLoginIntegrationWithMantis() throws IOException, ServiceException {
    String bugId = "0000004";
    skipIfNotFixed(Integer.valueOf(bugId));
    HttpSession session = app.newSession();
    assertTrue(session.login(app.getProperty("mantis.admin"), app.getProperty("mantis.password")));
    assertTrue(session.isLoggedInAs(app.getProperty("mantis.admin")));
  }
}
