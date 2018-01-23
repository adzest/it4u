package com.javaProject.spreadSheet;

import javax.swing.*;
import javax.swing.table.*;


public class SheetTableModel extends AbstractTableModel
{

  Sheet sheet;

  public SheetTableModel(Sheet sheet) {
    this.sheet = sheet;
  }

  public int getRowCount() {
    return 100;
  }

  public int getColumnCount() {
    return 50;
  }

  public String getLiteralValueAt(int row, int column)
  {
    return sheet.getLiteral(getCellName(row, column));
  }

  public Object getValueAt(int row, int column)
  {
    if (column == 0) {
      return getRowName(row);
    }

    // get value from sheet
    return sheet.get(getCellName(row, column));
  }

  public void setValueAt(Object value, int row, int column)
  {
    sheet.put(getCellName(row, column), (String)value);
    fireTableCellUpdated(row, column);
  }

  private String getCellName(int row, int column)
  {
    return getColumnName(column) + getRowName(row);
  }

  private String getRowName(int row)
  {
    return "" + (row+1);
  }

  /**
   * Returns a default name for the column using spreadsheet
   * conventions: A, B, C, ... Z, AA, AB, etc. If column cannot be
   * found, returns an empty string.
   **/
  public String getColumnName(int column) {
    if (column < 0)
      // throw new IllegalArgumentException("column must be >= 0");
      return "";

    if (column == 0)
      return "";

    int index = column - 1;	// go to zero-based alphabet

    if (index < 26)
      return "" + getColumnChar(index);

    // todo: loop for more than 26*26 columns
    return  ""
          + getColumnChar(index / 26 - 1)
          + getColumnChar(index % 26)
          ;
  }

  private char getColumnChar(int letter) {
    return (char) ('A' + letter);
  }
}