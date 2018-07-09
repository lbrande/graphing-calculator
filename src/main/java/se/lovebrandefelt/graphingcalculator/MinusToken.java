package se.lovebrandefelt.graphingcalculator;

class MinusToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double a, double b) {
    return a - b;
  }
}
