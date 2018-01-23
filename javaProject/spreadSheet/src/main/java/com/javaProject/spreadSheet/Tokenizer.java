package com.javaProject.spreadSheet;

import java.util.*;

public class Tokenizer {

  private String pushed = null;

  private StringTokenizer tokens;

  public Tokenizer(String s) {
    tokens = new StringTokenizer(s, "()*/+-", true);
  }

  /**
   * @return null if no more tokens left
   **/
  public String nextToken()
  {
    String token;
    if (pushed != null) {
      token = pushed;
      pushed = null;
    }
    else {
      token = "";
      while (token.equals("")) {
        try {
          token = tokens.nextToken().trim();
        }
        catch (NoSuchElementException e) {
          return null;
        }
      }
    }
    //System.out.println("'" + token + "'");
    return token;
  }

  public void pushBack(String token) {
    pushed = token;
  }

  public boolean hasMoreTokens() {
    return tokens.hasMoreTokens() || (pushed != null);
  }

}