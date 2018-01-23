package com.java4qa.mantis.com.java4qa.mantis.tests;


import com.java4qa.mantis.com.java4qa.mantis.appmanager.ApplicationManager;
import com.java4qa.mantis.com.java4qa.mantis.model.Issue;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {

  protected static final ApplicationManager app
      = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
//    app.ssh().upload(new File("src/test/resources/config_inc.php"), "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php", "/Applications/XAMPP/xamppfiles/htdocs/mantisbt-1.2.19/config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
//     app.ssh().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  private boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    boolean answer = false;
    Set<Issue> issueSet = app.soap().getIssues();
    for (Issue issue : issueSet){
      System.out.println(issue.toString());
      if (issue.getStatus()!=null) {
        answer = true;
      }
    }
    return answer;
  }

  public void skipIfNotFixed(int issueId) throws SkipException, RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}