package com.java4qa.rest.Tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.java4qa.rest.model.Issue;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase{

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic(
        app.getProperty("rest.login"),
        ""
    );
    System.out.println(app.getProperty("rest.login"));
    System.out.println("");
  }

  @Test
  public void testCreateIssue() {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues.size(), oldIssues.size());
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() {
    String json = RestAssured.given()
        .parameter("limit",app.getProperty("rest.limit"))
        .get(String.format("%s%s", app.getProperty("rest.api.url"), app.getProperty("rest.prefix.issues")))
        .asString();
    System.out.println(json);
    //?limit=1000
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  private int createIssue(Issue newIssue) {
    String json = RestAssured.given()
        .parameter("subject", newIssue.getSubject())
        .parameter("description", newIssue.getDescription())
        .post(String.format("%s%s", app.getProperty("rest.api.url"), app.getProperty("rest.prefix.issues")))
        .asString();
    System.out.println(json);
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
