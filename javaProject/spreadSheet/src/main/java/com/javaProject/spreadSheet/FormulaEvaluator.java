package com.javaProject.spreadSheet;

import java.util.NoSuchElementException;
import java.util.Stack;

// parses according to a top-down (i think) grammar:
// Expression = Term ([+-] Term)*
// Term = Factor ([*/] Factor)*
// Factor = Number | \( Expression \)

public class FormulaEvaluator {

  public class CircularReferenceException extends RuntimeException {
    public CircularReferenceException(String msg) {
      super("Circular reference to " + msg);
    }
  }

  public class FormulaParsingException extends RuntimeException {
    public FormulaParsingException(String msg) {
      super(msg);
    }
  }

  public Sheet sheet;
  public Stack cellsVisiting = new Stack();

  public FormulaEvaluator(Sheet sheet) {
    this.sheet = sheet;
  }

  private void logError(Throwable e) {
    //System.out.println(e);
    //e.printStackTrace();
  }

  /**
   * @param formula the formula to evaluate; note that "A1" is a
   *                valid formula, so you can pass in a cell reference too and it
   *                will evaluate its contents
   **/
  public String evaluate(String formula) {
    try {
      return evaluateFormula(formula);
    } catch (NoSuchElementException e) {
      logError(e);
      return "#Error";
    } catch (CircularReferenceException e) {
      logError(e);
      return "#Circular";
    } catch (FormulaParsingException e) {
      logError(e);
      return "#Error";
    }
  }

  private String evaluateFormula(String formula) {
    Tokenizer tokens = new Tokenizer(formula);
    return evaluateExpression(tokens);
  }

  private String evaluateCell(String whichCell) {
    String value = sheet.getLiteral(whichCell);

    if (Sheet.isFormula(value)) {
      if (cellsVisiting.contains(whichCell)) {
        throw new CircularReferenceException(whichCell);
      }

      // add it to the stack of cells we're in the middle of
      // evaluating
      cellsVisiting.push(whichCell);
      // now try to evaluate its formula
      String evaluated = evaluateFormula(value.substring(1));
      // we're done evaluating it, so remove it from our stack
      String popped = (String) cellsVisiting.pop();
      if (popped != whichCell) {
        throw new FormulaParsingException("Expected '" + whichCell + "' but popped '" + popped + "'");
      }
      return evaluated;
    } else {
      // return its literal value
      return value;
    }
  }

  private interface Evaluator {
    public String eval(Tokenizer tokens);
  }

  private class TermEvaluator implements Evaluator {
    public String eval(Tokenizer tokens) {
      return evaluateTerm(tokens);
    }
  }

  private class FactorEvaluator implements Evaluator {
    public String eval(Tokenizer tokens) {
      return evaluateFactor(tokens);
    }
  }


  private String evaluateExpression(Tokenizer tokens) {
    return evaluateOperation(tokens, new TermEvaluator(), "+", "-");
  }

  private String evaluateTerm(Tokenizer tokens) {
    return evaluateOperation(tokens, new FactorEvaluator(), "*", "/");
  }

  // I used virtually the same code inside evaluateTerm and
  // evaluateExpression, so I factored it out here.  It evaluates a
  // series of same-precedence operations; when it hits an operator
  // that doesn't match, it returns with its current result.  Since
  // it's recursive, we can string together many levels of
  // precedence (someday).
  private String evaluateOperation(Tokenizer tokens, Evaluator evaluator, String op1, String op2) {
    if (!tokens.hasMoreTokens())
      return null;

    String left = evaluator.eval(tokens);
    long valLeft = Long.parseLong(left);

    while (tokens.hasMoreTokens()) {
      String operator = tokens.nextToken();
      if (operator == null) {
        // we wanted an operator, but if all we got was the
        // end of the token stream (all whitespace), that's OK
        // too
        break;
      }
      if (operator.equals(op1) || operator.equals(op2)) {
        String right = evaluator.eval(tokens);
        long valRight = Long.parseLong(right);
        valLeft = operate(operator, valLeft, valRight);
      } else {
        tokens.pushBack(operator);
        break;
      }
    }

    return "" + valLeft;
  }

  // a factor is either a paren-expression, or a terminal (number or
  // cell ref)
  private String evaluateFactor(Tokenizer tokens) {
    String token = tokens.nextToken();
    if (token == null)
      throw new FormulaParsingException("Premature end of formula (expecting factor)");
    if (token.equals("(")) {
      // spawn a new expression evaluator context, which will
      // stop just before the close paren
      String val = evaluateExpression(tokens);
      // now gobble the close paren
      token = tokens.nextToken();
      if (token == null)
        throw new FormulaParsingException("Premature end of formula (expecting ')')");
      if (!token.equals(")")) {
        throw new FormulaParsingException("Expecting ')', got '" + token + "'");
      }
      return val;
    } else {
      if (Sheet.isNumber(token)) {
        return token;
      } else {
        // anything that's not a number, assume it's a cell
        // reference.
        return evaluateCell(token);
      }
    }
  }

  private long operate(String operator, long left, long right) {
    if (operator.equals("+")) {
      return left + right;
    } else if (operator.equals("-")) {
      return left - right;
    } else if (operator.equals("*")) {
      return left * right;
    } else if (operator.equals("/")) {
      return left / right;
    } else {
      throw new IllegalArgumentException("Illegal operator '" + operator + "'");
    }
  }


}