package com.java4qa.mantis.com.java4qa.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import com.java4qa.mantis.com.java4qa.mantis.model.Issue;
import com.java4qa.mantis.com.java4qa.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }


  public Set<Project> getProject() throws ServiceException, MalformedURLException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("mantis.admin"), app.getProperty("mantis.password"));
    return Arrays.asList(projects).stream().map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());

  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("mantisresources.url")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(app.getProperty("mantis.admin"), app.getProperty("mantis.password"),
        BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(app.getProperty("mantis.admin"), app.getProperty("mantis.password"), issueData);
    IssueData createdIssueData = mc.mc_issue_get(app.getProperty("mantis.admin"), app.getProperty("mantis.password"), issueId);
    return new Issue().withId(createdIssueData.getId().intValue())
        .withSummary(createdIssueData.getSummary())
        .withDescription(createdIssueData.getSummary())
        .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
            .withName(createdIssueData.getProject().getName()));

  }

  public Set<Issue> getIssues() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData[] issueData = mc.mc_project_get_issues(
        app.getProperty("mantis.admin"),
        app.getProperty("mantis.password"),
        null,
        null,
        null);
    System.out.println(issueData);
    Set<Issue> resultSet = Arrays.stream(issueData).map((i) -> new Issue()
        .withId(i.getId().intValue())
        .withSummary(i.getSummary())
        .withDescription(i.getDescription())).collect(Collectors.toSet());
    return resultSet;
  }
}
