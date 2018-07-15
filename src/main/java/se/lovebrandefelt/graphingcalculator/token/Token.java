package se.lovebrandefelt.graphingcalculator.token;

import java.util.Objects;

public abstract class Token {
  private static final String NOT_NUMERIC_ERROR_MESSAGE = "Token is non-numeric.";
  private static final String NOT_BINARY_OPERATOR_ERROR_MESSAGE = "Token is not a binary operator.";

  public boolean isNumeric() {
    return false;
  }

  public double getNumericValue() {
    throw new UnsupportedOperationException(NOT_NUMERIC_ERROR_MESSAGE);
  }

  public boolean isBinaryOperator() {
    return false;
  }

  public double evaluate(double firstOperand, double secondOperand) {
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
