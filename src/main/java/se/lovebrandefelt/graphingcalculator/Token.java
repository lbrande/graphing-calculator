package se.lovebrandefelt.graphingcalculator;

abstract class Token {

  boolean isNumeric() {
    return false;
  }

  double getNumericValue() {
    throw new UnsupportedOperationException("Can't get numeric value of non-numeric token.");
  }

  boolean isBinaryOperator() {
    return false;
  }

  double evaluate(double a, double b) {
    throw new UnsupportedOperationException(
        "Can't evaluate non-binary-operator token with 2 operands");
  }
}
