package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class ContactAddToGroupTests extends TestBase {

  @BeforeTest(dependsOnGroups = "withGroup")
  private void ensureGroupExisted() {
    Groups groups = app.db().groups();
    if (groups.size() == 0) {
      GroupData group = new GroupData().withName("groupToContact");
      app.goTo().groupPage();
      app.group().create(group);
    }
  }

  @BeforeTest(dependsOnGroups = "withContact")
  private void ensureContactExisted() {
    Contacts contacts = app.db().contacts();
    if (contacts.size() == 0) {
      ContactData contact = new ContactData().withFirst("name_1").withLast("last_1");
      app.goTo().contactCreationPage();
      app.contact().create(contact, true);
    }
  }
//
//  @BeforeTest(dependsOnGroups = "withUngroupedContact")
//  private void ensureContactWhitoutGroupExisted() {
//    boolean isExisted = false;
//    Contacts contacts = app.db().contacts();
//    for (ContactData contact : contacts) {
//      if (contact.getGroups() == null) {
//        isExisted = true;
//      }
//    }
//    if (isExisted != true){
//      app.goTo().contactCreationPage();
//      ContactData contact = new ContactData().withFirst("name_1").withLast("last_1");
//      app.contact().create(contact, true);
//    }
//  }

  @Test(enabled = true, groups = {"withGroup", "withContact"})
  public void testContactAddToGroup() {
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();
    Integer contactId = contact.getId();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    Integer groupId = group.getId();
    app.goTo().homePage();
    app.contact().addToGroup(contact, group);

    // Verify that contacts and groups size is not changed
//    assertThat(app.contact().count(), equalTo(app.db().contacts().size()));
//    assertThat(app.group().count(), equalTo(groups.size()));

    // Verify that group added to contact
    assertTrue(app.db().isContactInGroup(contactId, groupId));
    verifyContactListInUI();
  }

}
