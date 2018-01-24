package com.java4qa.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.java4qa.rest.model.Issue;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RestAssuredTests extends TestBase {

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
  public void testCreateIssue() throws MalformedURLException, RemoteException {
    Set<Issue> oldIssues = getIssues();
    int bugId = oldIssues.iterator().next().getId();
    skipIfNotFixed(bugId);
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues.size(), oldIssues.size());
    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testCloseIssue() throws MalformedURLException, RemoteException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("subIsa");
    int issueId = createIssue(newIssue);
    int issueIdToUpdate = getIssues().stream().filter(i -> i.getId() == issueId).findFirst().get().getId();
    closeIssue(issueIdToUpdate);
    Issue issueIdStatus = getIssueByid(issueId);
    assertTrue(issueIdStatus.getStatus().toLowerCase().equals("closed"));
  }

  private Issue getIssueByid(int issueId) throws MalformedURLException, RemoteException {
    String urlPath = String.format(
        "%s/%s/%s.%s",
        app.getProperty("rest.api.url"),
        app.getProperty("rest.prefix.issues"),
        issueId,
        app.getProperty("rest.prefix.format"));
    String json = RestAssured.given()
        .get(urlPath)
        .asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonArray jIssueArray = (JsonArray) parsed.getAsJsonObject().get("issues");
    JsonElement jIssue = jIssueArray.get(0);
    Issue issueIdStatus = new Issue()
        .withId(Integer.valueOf(jIssue.getAsJsonObject().get("id").getAsString()))
        .withStatus(jIssue.getAsJsonObject().get("state_name").getAsString());
    return issueIdStatus;
  }

  private Set<Issue> getIssues() throws MalformedURLException, RemoteException {
    String urlPath = String.format(
        "%s/%s.%s",
        app.getProperty("rest.api.url"),
        app.getProperty("rest.prefix.issues"),
        app.getProperty("rest.prefix.format"));
    String json = RestAssured.given()
        .parameter("limit", app.getProperty("rest.limit"))
        .get(urlPath)
        .asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private int createIssue(Issue newIssue) {
    String urlPath = String.format(
        "%s/%s.%s",
        app.getProperty("rest.api.url"),
        app.getProperty("rest.prefix.issues"),
        app.getProperty("rest.prefix.format"));
    String json = RestAssured.given()
        .parameter("subject", newIssue.getSubject())
        .parameter("description", newIssue.getDescription())
        .post(urlPath)
        .asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  private void closeIssue(int issueIdToUpdate) {
    String urlPath = String.format(
        "%s/%s/%s.%s",
        app.getProperty("rest.api.url"),
        app.getProperty("rest.prefix.issues"),
        issueIdToUpdate,
        app.getProperty("rest.prefix.format"));
    String json = RestAssured.given()
        .parameter("method", "update")
        .parameter("issue[state]", 3)
        .post(urlPath)
        .asString();
  }

  private Set<JsonElement> getIssuesJson() {
    String urlPath = String.format(
        "%s/%s.%s",
        app.getProperty("rest.api.url"),
        app.getProperty("rest.prefix.issues"),
        app.getProperty("rest.prefix.format"));
    String json = RestAssured.given()
        .parameter("limit", app.getProperty("rest.limit"))
        .get(urlPath)
        .asString();
    System.out.println(urlPath);
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<JsonElement>>() {
    }.getType());
  }

  private boolean isIssueOpen(int issueId) throws RemoteException, MalformedURLException {
    boolean answer = true;
    Set<JsonElement> issuesJson = getIssuesJson();
    for (JsonElement issue : issuesJson) {
      int jIssueId = issue.getAsJsonObject().get("id").getAsInt();
      if (jIssueId == issueId) {
        String jIssueStateName = issue.getAsJsonObject().get("state_name").toString().toLowerCase();
        if (!jIssueStateName.equals("open")) {
          answer = false;
        }
        break;
      }
    }
    return answer;
  }

  public void skipIfNotFixed(int issueId) throws SkipException, RemoteException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
