package se.lovebrandefelt.graphingcalculator;

import java.util.Objects;

abstract class Token {
  private static final String NOT_NUMERIC_ERROR_MESSAGE = "Token is non-numeric.";
  private static final String NOT_BINARY_OPERATOR_ERROR_MESSAGE = "Token is not a binary operator";

  boolean isNumeric() {
    return false;
  }

  double getNumericValue() {
    throw new UnsupportedOperationException(NOT_NUMERIC_ERROR_MESSAGE);
  }

  boolean isBinaryOperator() {
    return false;
  }

  double evaluate(double firstOperand, double secondOperand) {
    throw new UnsupportedOperationException(NOT_BINARY_OPERATOR_ERROR_MESSAGE);
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
