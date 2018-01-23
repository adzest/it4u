package com.java4qa.addressbook.tests;

import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }
  
  @Test()
  public void testGroupModification() {
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
          .withId(modifiedGroup.getId()).withName("test1_mod").withHeader("test2").withFooter("test3");
    app.goTo().groupPage();
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    //TODO:DONE - Chapter 5 video 6: replace 2 methods with one withModified.
    assertThat(after, equalTo(before.withModified(modifiedGroup, group)));
    verifyGroupListInUI();
  }

}
