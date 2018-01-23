package com.javaProject.spreadSheet;

import java.util.*;
import java.io.*;

public class Sheet
{
  Map cells = new HashMap();

  public void put(String whichCell, String value) {
    cells.put(whichCell, value);
  }

  public String get(String whichCell) {
    String value = getLiteral(whichCell);

    if (value == null)
      return "";

    if (isNumber(value)) {
      value = value.trim();
    }

    if (isFormula(value)) {
      return new FormulaEvaluator(this).evaluate(whichCell);
    }

    return value;
  }

  public String getLiteral(String whichCell) {
    String value = (String)cells.get(whichCell);
    return (value==null) ? "" : value;
  }

  public String getFormula(String whichCell) {
    return getLiteral(whichCell).trim();
  }

  static boolean isNumber(String value) {
    value = value.trim();
    try {
      Long x = new Long(value);
    }
    catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  static boolean isFormula(String value) {
    return (value.startsWith("="));
  }

}
