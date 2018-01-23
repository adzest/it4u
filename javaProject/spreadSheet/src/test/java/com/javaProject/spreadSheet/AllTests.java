package com.javaProject.spreadSheet;

import junit.framework.*;

/**
 * TestSuite that runs all the tests
 *
 * @version $Id: AllTests.java,v 1.5 2002/01/22 18:14:40 alex Exp $
 */
public class AllTests {

  public static void main (String[] args) {
    junit.textui.TestRunner.run (suite());
  }

  public static Test suite ( ) {
    TestSuite suite = new TestSuite("All Wake Tests");
    suite.addTest(new TestSuite(TestTokenizer.class));
    suite.addTest(new TestSuite(TestOne.class));
    suite.addTest(new TestSuite(TestTwo.class));
    suite.addTest(new TestSuite(TestThree.class));
    suite.addTest(new TestSuite(TestFour.class));
    return suite;
  }
}