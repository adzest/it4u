package com.javaProject.spreadSheet;

import junit.framework.TestCase;

/**
 * @version $Id: TestOne.java,v 1.5 2002/01/18 18:55:29 alex Exp $
 **/
public class TestOne extends TestCase {

  public TestOne(String name) {
    super(name);
  }

  public void test1A_EmptyCells() {
    Sheet sheet = new Sheet();
    assertEquals("", sheet.get("A1"));
    assertEquals("", sheet.get("ZX347"));
  }

  // Implement each test before going to the next one.
  public void test1B_TextCells() {
    Sheet sheet = new Sheet();
    String theCell = "A21";

    sheet.put(theCell, "A string");
    assertEquals("A string", sheet.get(theCell));

    sheet.put(theCell, "A different string");
    assertEquals("A different string", sheet.get(theCell));

    sheet.put(theCell, "");
    assertEquals("", sheet.get(theCell));
  }

  // Implement each test before going to the next one.
  // You can split this test case if it helps.

  public void test1C_NumericCells() {
    Sheet sheet = new Sheet();
    String theCell = "A21";

    sheet.put(theCell, "X99"); // "Obvious" string
    assertEquals(sheet.get(theCell), "X99");

    sheet.put(theCell, "14"); // "Obvious" number
    assertEquals(sheet.get(theCell), "14");

    sheet.put(theCell, " 99 X"); // Whole string must be numeric
    assertEquals(sheet.get(theCell), " 99 X");

    sheet.put(theCell, " 1234 "); // Blanks ignored
    assertEquals(sheet.get(theCell), "1234");

    sheet.put(theCell, " "); // Just a blank
    assertEquals(sheet.get(theCell), " ");
  }

  public void test1D_LiteralValues() { // Used to edit cell
    Sheet sheet = new Sheet();
    String theCell = "A21";

    sheet.put(theCell, "Some string");
    assertEquals(sheet.getLiteral(theCell), "Some string");

    sheet.put(theCell, " 1234 ");
    assertEquals(sheet.getLiteral(theCell), " 1234 ");

    sheet.put(theCell, "=7"); // Foreshadowing formulas:)
    assertEquals(sheet.getLiteral(theCell), "=7");
  }

  // We'll talk about "get" and formulas next time
}