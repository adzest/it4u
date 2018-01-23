package com.javaProject.spreadSheet;

import junit.framework.TestCase;

/**
 * @version $Id: TestTwo.java,v 1.9 2002/01/21 21:40:16 alex Exp $
 **/
public class TestTwo extends TestCase {
  public TestTwo(String name) {
    super(name);
  }

  // 2A
  public void testManyCells() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "8");
    sheet.put("X27", "10");
    sheet.put("ZX901", "4");

    assertEquals("A1", "8", sheet.get("A1"));
    assertEquals("X27", "10", sheet.get("X27"));
    assertEquals("ZX901", "4", sheet.get("ZX901"));

    sheet.put("A1", "12");
    assertEquals("A1 after", "12", sheet.get("A1"));
    assertEquals("X27 same", "10", sheet.get("X27"));
    assertEquals("ZX901 same", "4", sheet.get("ZX901"));
  }

  // Implement code for previous test before moving to next one.
  // 2B
  public void testFormulaSpec() {
    Sheet sheet = new Sheet();
    sheet.put("B1", " =7"); // note leading space
    assertEquals("Not a formula", " =7", sheet.get("B1"));
    assertEquals("Unchanged", " =7", sheet.getLiteral("B1"));
  }

  // Next - start on parsing expressions
  // 2C
  public void testConstantFormula() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=7");
    assertEquals("Formula", "=7", sheet.getLiteral("A1"));
    assertEquals("Value", "7", sheet.get("A1"));
  }

  // More formula tests. You may feel the need to make up
  // additional intermediate test cases to drive your code
  // better. (For example, you might want to test "2*3"
  // before "2*3*4".) That's fine, go ahead and create them.
  // Just keep moving one test at a time.

  // Order of tests - I'm familiar enough with parsing to think
  // it's probably better to do them in this order (highest
  // precedence to lowest). For extra credit, you might redo
  // this part of the exercise with the tests in a different order
  // to see what difference it makes.

  // 2D
  public void testParentheses() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=(7)");
    assertEquals("Parends", "7", sheet.get("A1"));
  }

  // 2E
  public void testDeepParentheses() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=((((10))))");
    assertEquals("Parends", "10", sheet.get("A1"));
  }

  // 2F
  public void testMultiply() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=2*3*4");
    assertEquals("Times", "24", sheet.get("A1"));
  }

  // 2G
  public void testAdd() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=71+2+3");
    assertEquals("Add", "76", sheet.get("A1"));
  }

  // 2H
  public void testPrecedence() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=7+2*3");
    assertEquals("Precedence", "13", sheet.get("A1"));
  }

  public void testPrecedence2() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=7*2+3");
    assertEquals("Precedence", "17", sheet.get("A1"));
  }

  // 2I
  public void testFullExpression() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=7*(2+3)*((((2+1))))");
    assertEquals("Expr", "105", sheet.get("A1"));
  }

  // Add any test cases you feel are missing based on
  // where your code is now.

  public void testDivide() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=21/3");
    assertEquals("Divide", "7", sheet.get("A1"));
  }

  public void testDivideAndMultiply() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=21/3*4");
    assertEquals("Divide and Multiply", "28", sheet.get("A1"));
  }

  public void testDivideAndMultiplyWithParens() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=21/(3*4)");
    assertEquals("Divide and Multiply", "1", sheet.get("A1"));
  }

  public void testDivideAndMultiplyWithSpaces() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=21 / ( 3 * 4 ) ");
    assertEquals("Divide and Multiply", "1", sheet.get("A1"));
  }

  // Then try your hand at a few test cases: Add "-" and "/"
  // with normal precedence.

  // Next, error handling.
  public void testSimpleFormulaError() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=7*");
    assertEquals("Error", "#Error", sheet.get("A1"));
  }

  public void testParenthesisError() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "=(((((7))");
    assertEquals("Error", "#Error", sheet.get("A1"));
  }

  // Add any more error cases you need. Numeric errors (e.g.,
  // divide by 0) can return #Error too.

  // Take a deep breath and refactor. This was a big jump.
  // Next time we'll tackle formulas involving cells.

}
