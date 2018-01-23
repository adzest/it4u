package com.java4qa.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @BeforeTest(dependsOnGroups = "withGroup")
  private void preconditionEnsureThatOneGroupExisted() {
    Groups groups = app.db().groups();
    if (groups.size() == 0) {
      GroupData group = new GroupData().withName("test1");
      app.goTo().groupPage();
      app.group().create(group);
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/validContacts.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/validContacts.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
    return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }

  @Test(enabled = true, dataProvider = "validContactsFromJson", groups = "withGroup")
  public void testContactCreationFull(ContactData newContact) {
    Contacts before = app.db().contacts();
    app.goTo().contactCreationPage();
    app.contact().create(newContact, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    //noinspection ConstantConditions
    assertThat(after, equalTo(
          before.withAdded(newContact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
    verifyContactListInUI();
  }

//  @Test(enabled = true)
//  public void testContactCreationWithGroup() {
////    TODO: add ensurePreconditions() for this test. Groups should be existed!
//    Contacts before = app.contact().all();
//    app.goTo().contactCreationPage();
//    ContactData contact = new ContactData()
//          .withFirst("test_name").withLast("test_surname")
////          .withGroup("test1")
//          ;
//    app.contact().create(contact, true);
//    assertThat(app.contact().count(), equalTo(before.size() + 1));
//    Contacts after = app.contact().all();
//    //noinspection ConstantConditions
//    assertThat(after, equalTo(
//          before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
//    verifyContactListInUI();
//  }
//
//  @Test(enabled = true)
//  public void testContactCreationWithPones() {
//    Contacts before = app.contact().all();
//    app.goTo().contactCreationPage();
//    ContactData contact = new ContactData()
//          .withFirst("test_name").withLast("test_surname").withHomePhone("111").withMobilePhone("222").withWorkPhone("333");
//    app.contact().create(contact, true);
//    assertThat(app.contact().count(), equalTo(before.size() + 1));
//    Contacts after = app.contact().all();
//    //noinspection ConstantConditions
//    assertThat(after, equalTo(
//          before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
//    verifyContactListInUI();
//  }
//
//  @Test(enabled = true)
//  public void testContactCreationWithOutGroup() {
//    Contacts before = app.contact().all();
//    app.goTo().contactCreationPage();
//    ContactData contact = new ContactData()
//          .withFirst("name").withLast("surname").withAddress("companyAddress")
//          .withHomePhone("+111").withMobilePhone("(22)2").withWorkPhone("3-3 3")
//          .withEmail2("email@2").withEmail3("email@3");
//    app.contact().create(contact, true);
//    assertThat(app.contact().count(), equalTo(before.size() + 1));
//    Contacts after = app.contact().all();
//    //noinspection ConstantConditions
//    assertThat(after, equalTo(
//          before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
//    verifyContactListInUI();
//  }
}
