package com.java4qa.rest.Tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.java4qa.rest.model.Issue;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues.size(), oldIssues.size());
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {
    Request request = Request.Get(
        String.format(
            "%s%s?limit=%s",
            app.getProperty("rest.api.url"),
            app.getProperty("rest.prefix.issues"),
            app.getProperty("rest.limit"))
    );
    String json = gteExecutor().execute(request).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor gteExecutor() {
    return Executor.newInstance().auth(app.getProperty("rest.login"), "");
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = gteExecutor().execute(Request
        .Post(String.format(
            "%s%s",
            app.getProperty("rest.api.url"),
            app.getProperty("rest.prefix.issues"))
        ).bodyForm(
            new BasicNameValuePair("subject", newIssue.getSubject()),
            new BasicNameValuePair("description", newIssue.getDescription())
        )).returnContent().asString();
    System.out.println(json);
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
