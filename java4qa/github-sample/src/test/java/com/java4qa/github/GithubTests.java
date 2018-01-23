package com.java4qa.github;

import com.jcabi.github.*;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.testng.ReporterConfig;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public  void testCommits() throws IOException {
    Github github = new RtGithub("6b634664bb2675e2aba680db53cd19eb802d5b89");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("adzest", "addressbook")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
