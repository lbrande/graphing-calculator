package se.lovebrandefelt.graphingcalculator.token;

import java.util.Objects;

public abstract class Token {
  private static final String NOT_NUMERIC_ERROR_MESSAGE = "%s is non-numeric.";
  private static final String NOT_VARIABLE_ERROR_MESSAGE = "%s is not a variable.";
  private static final String NOT_BINARY_OPERATOR_ERROR_MESSAGE = "%s is not a binary operator.";

  public boolean isNumeric() {
    return false;
  }

  public double getNumericValue() {
    throw new UnsupportedOperationException(String.format(NOT_NUMERIC_ERROR_MESSAGE, toString()));
  }

  public boolean isVariable() {
    return false;
  }

  public char getVariableChar() {
    throw new UnsupportedOperationException(String.format(NOT_VARIABLE_ERROR_MESSAGE, toString()));
  }

  public boolean isBinaryOperator() {
    return false;
  }

  public double evaluate(double firstOperand, double secondOperand) {
    throw new UnsupportedOperationException(
        String.format(NOT_BINARY_OPERATOR_ERROR_MESSAGE, toString()));
  }

  public int getPrecedence() {
    throw new UnsupportedOperationException(
        String.format(NOT_BINARY_OPERATOR_ERROR_MESSAGE, toString()));
  }

  public Associativity getAssociativity() {
    throw new UnsupportedOperationException(
        String.format(NOT_BINARY_OPERATOR_ERROR_MESSAGE, toString()));
  }

  public boolean isLeftParen() {
    return false;
  }

  public boolean isRightParen() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }
}
