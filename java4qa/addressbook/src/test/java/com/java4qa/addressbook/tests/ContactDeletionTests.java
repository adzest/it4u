package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().contactCreationPage();
      app.contact().create(new ContactData()
            .withFirst("test_name").withLast("test_surname"), true);
    }
  }

  @Test(invocationCount = 1)
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    System.out.println(deletedContact.getId());
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.contact().all();
    Contacts cleanBefore = before.withOut(deletedContact);
    assertThat(after, equalTo(cleanBefore));
    verifyContactListInUI();
  }
}
