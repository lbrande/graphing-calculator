package se.lovebrandefelt.graphingcalculator;

class PowToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return Math.pow(firstOperand, secondOperand);
  }
}
