package com.javaProject.spreadSheet;

import junit.framework.TestCase;

/**
 * @version $Id: TestThree.java,v 1.2 2002/01/21 21:40:16 alex Exp $
 **/
public class TestThree extends TestCase {
  public TestThree(String name) {
    super(name);
  }

  Sheet sheet;

  public void setUp() {
    sheet = new Sheet();
  }

  public void testThatCellReferenceWorks() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "8");
    sheet.put("A2", "=A1");
    assertEquals("cell lookup", "8", sheet.get("A2"));
  }

  public void testThatCellChangesPropagate() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "8");
    sheet.put("A2", "=A1");
    assertEquals("cell lookup", "8", sheet.get("A2"));

    sheet.put("A1", "9");
    assertEquals("cell change propagation", "9", sheet.get("A2"));
  }

  public void testThatFormulasKnowCellsAndRecalculate() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "8");
    sheet.put("A2", "3");
    sheet.put("B1", "=A1*(A1-A2)+A2/3");
    assertEquals("calculation with cells", "41", sheet.get("B1"));

    sheet.put("A2", "6");
    assertEquals("re-calculation", "18", sheet.get("B1"));
  }

  public void testThatDeepPropagationWorks() {
    Sheet sheet = new Sheet();
    sheet.put("A1", "8");
    sheet.put("A2", "=A1");
    sheet.put("A3", "=A2");
    sheet.put("A4", "=A3");
    assertEquals("deep propagation", "8", sheet.get("A4"));

    sheet.put("A2", "6");
    assertEquals("deep re-calculation", "6", sheet.get("A4"));
  }


  // The following test is likely to pass already.
  public void testThatFormulaWorksWithManyCells() {
    sheet.put("A1", "10");
    sheet.put("A2", "=A1+B1");
    sheet.put("A3", "=A2+B2");
    sheet.put("A4", "=A3");
    sheet.put("B1", "7");
    sheet.put("B2", "=A2");
    sheet.put("B3", "=A3-A2");
    sheet.put("B4", "=A4+B3");

    assertEquals("multiple expressions - A4", "34", sheet.get("A4"));
    assertEquals("multiple expressions - B4", "51", sheet.get("B4"));
  }

  // Refactor and get all the formula stuff nice and clean.


  // Next: (I almost made this a separate part, and when I
  // originally did it, I did it in a different design session).

  // There's one big open issue for formulas: what about
  // circular references?

  // I'll sketch some hints, but you should define your own tests
  // that drive toward a solution compatible with your own
  // implementation.
  public void testThatCircularReferenceDoesntCrash() {
    sheet.put("A1", "=A1");
    assertTrue(true);
  }

  // Just like errors return a special value, it might be nice
  // if circular references did too. (See notes below).
  public void testThatCircularReferencesAdmitIt() {
    sheet.put("A1", "=A1");
    assertEquals("Detect circularity", "#Circular", sheet.get("A1"));
  }

  // You might come up with some other approach that suits your
  // taste. We won't be exploring this corner of the solution
  // any further; you just want a scheme that blocks silly mistakes.
  // Make sure you test deep circularities involving partially
  // evaluated expressions.

  // A hint: if you blindly evaluate an expression you have no
  // control over how deep the expression can be, since
  // circular references appear to be infinitely deep.

  public void testCircularReference() {
    sheet.put("A1", "=A2");
    sheet.put("A2", "=A3");
    sheet.put("A3", "=A1");
    assertEquals("Detect circularity", "#Circular", sheet.get("A1"));
  }

  public void testCircularReferenceWithinFormula() {
    sheet.put("A1", "=A2+A3");
    sheet.put("A2", "=2+3");
    sheet.put("A3", "=7*A1");
    assertEquals("Detect circularity", "#Circular", sheet.get("A1"));
  }

  public void testDoubleReferenceWorks() {
    sheet.put("A1", "=A2+A3");
    sheet.put("A2", "=2+3");
    sheet.put("A3", "=A2*5");
    assertEquals("Double Ref OK", "30", sheet.get("A1"));
  }

  public void testDoubleReferenceWorks2() {
    sheet.put("A1", "=A2+A2");
    sheet.put("A2", "=2+3");
    assertEquals("Double Ref OK", "10", sheet.get("A1"));
  }

  // Where are we? I intend to spend the next two parts hooking
  // up a GUI. Then there will be an optional part that pushes
  // things in an unexpected direction just to get a sense
  // of our software's robustness.


  // Thanks to all participants; I really enjoy hearing from
  // you and seeing your code.


}
