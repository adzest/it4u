package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCompareViewTests extends TestBase {

  @BeforeTest(dependsOnGroups = "withGroup")
  private void preconditionEnsureThatOneGroupExisted() {
    Groups groups = app.db().groups();
    if (groups.size() == 0) {
      GroupData group = new GroupData().withName("test1");
      app.goTo().groupPage();
      app.group().create(group);
    }
  }

  @BeforeMethod(dependsOnGroups = "withOneNewContact")
  public void ensurePreconditions() {
      app.goTo().groupPage();
      List<String> groupList = app.db().groups().stream().map(GroupData::getName).collect(Collectors.toList());
      if (groupList == null) {
        app.group().create(new GroupData().withName("test1"));
      }
    app.goTo().contactCreationPage();
    //TODO: Add Data without Group field to the Contact
    ContactData newContact = new ContactData()
        .withFirst("name").withMiddle("middle").withLast("surname").withAddress("companyAddress")
        .withNick("nickName").withTitle("title").withCompany("company").withAddress("address")
        .withHomePhone("+111").withMobilePhone("(22)2").withWorkPhone("3-3 3").withFax("fax")
        .withEmail2("email2").withEmail3("email3").withHomePage("homePage")
        .withBDay(1).withBMonth("January").withBYear(2010)
        .withADay(28).withAMonth("December").withAYear(2000)
        .inGroups(new GroupData().withName(groupList.iterator().next()));
        app.contact().create(newContact ,true);
  }


  @Test(enabled = false, groups = {"withGroup", "withOneNewContact"})
  public void testContactCompare() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    ContactData contactDetailsPage = app.contact().infoFromDetailsPage(contact);
    ContactData edit = contactInfoFromEditForm
        .withAllPhones(mergePhones(contactInfoFromEditForm)).withAllEmails(mergeEmails(contactInfoFromEditForm));
    ContactData details = contactDetailsPage
        .withAllPhones(mergePhones(contactDetailsPage)).withAllEmails(mergeEmails(contactDetailsPage));
//    System.out.println("edit: " + edit);
//    System.out.println("details: " + details);
    assertThat(edit, equalTo(details));
  }

//  public ContactData merge(ContactData contact) {
//    String mergedPhones = Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
//          .filter((s) -> !s.equals(""))
//          .map(ContactCompareViewTests::cleaned)
//          .collect(Collectors.joining("\n"));
//    String mergedEmails = Stream.of(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
//          .filter((s) -> !s.equals(""))
//          .map(ContactCompareViewTests::cleaned)
//          .collect(Collectors.joining("\n"));;
//    return new ContactData().withId(contact.getId())
//          .withFirst(contact.getFirstName()).withLast(contact.getLastName()).withAddress(contact.getAddress())
//          .withAllPhones(mergedPhones).withAllEmails(mergedEmails)
//          .withGroup(contact.getGroup());
//  }
}
