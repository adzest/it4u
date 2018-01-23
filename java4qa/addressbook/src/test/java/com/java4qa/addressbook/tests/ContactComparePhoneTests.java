package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactComparePhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().contactCreationPage();
      //TODO: Add Phone number to the contact
      app.contact().create(new ContactData()
            .withFirst("test_name").withLast("test_surname")
            .withHomePhone("111").withMobilePhone("222").withWorkPhone("333"), true);
    }
  }

  @Test
  public void testContactPhones() {
    //TODO: DONE - add check is there are any contact?
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

}
