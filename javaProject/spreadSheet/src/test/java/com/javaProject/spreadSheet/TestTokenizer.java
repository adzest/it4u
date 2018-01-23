package com.javaProject.spreadSheet;

import junit.framework.TestCase;

public class TestTokenizer extends TestCase {
  public TestTokenizer(String name) {
    super(name);
  }

  public void testNext() {
    Tokenizer tk = new Tokenizer("4+5*(6+17)");
    assertEquals(tk.nextToken(), "4");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "5");
    assertEquals(tk.nextToken(), "*");
    assertEquals(tk.nextToken(), "(");
    assertEquals(tk.nextToken(), "6");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "17");
    assertEquals(tk.nextToken(), ")");
    assertNull(tk.nextToken());
  }

  public void testSpace() {
    Tokenizer tk = new Tokenizer("4+ 5*(6+ 17) ");
    assertEquals(tk.nextToken(), "4");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "5");
    assertEquals(tk.nextToken(), "*");
    assertEquals(tk.nextToken(), "(");
    assertEquals(tk.nextToken(), "6");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "17");
    assertEquals(tk.nextToken(), ")");
    assertNull(tk.nextToken());
  }

  public void testPushBack() {
    Tokenizer tk = new Tokenizer("4+5*(6+17)");
    assertEquals(tk.nextToken(), "4");
    tk.pushBack("4");
    assertEquals(tk.nextToken(), "4");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "5");
    assertEquals(tk.nextToken(), "*");
    assertEquals(tk.nextToken(), "(");
    tk.pushBack("foo");
    assertEquals(tk.nextToken(), "foo");
    assertEquals(tk.nextToken(), "6");
    assertEquals(tk.nextToken(), "+");
    assertEquals(tk.nextToken(), "17");
    assertEquals(tk.nextToken(), ")");
    assertNull(tk.nextToken());
  }
}
