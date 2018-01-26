package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.appmanager.ApplicationManager;
import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
      = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  public static String cleaned(String contactFiled) {
    return contactFiled.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Start test " + m.getName());
  }

  public String mergePhones(ContactData contact) {
    return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhoneNum())
        .filter((s) -> !s.equals(""))
        .map(TestBase::cleaned)
        .collect(Collectors.joining("\n"));
  }

  public String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
        .filter((s) -> !s.equals(""))
        .map(TestBase::cleaned)
        .collect(Collectors.joining("\n"));
  }

  public static void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups
              .stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())
          )
      );
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts
              .stream()
              .map((c) -> new ContactData()
                  .withId(c.getId())
                  .withFirst(c.getFirstName())
                  .withLast(c.getLastName())
              )
              .collect(Collectors.toSet())
          )
      );
    }
  }
}