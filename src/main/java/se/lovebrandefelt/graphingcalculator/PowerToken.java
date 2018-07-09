package se.lovebrandefelt.graphingcalculator;

class PowerToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double a, double b) {
    return Math.pow(a, b);
  }
}
