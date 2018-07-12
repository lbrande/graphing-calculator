package se.lovebrandefelt.graphingcalculator;

public class DivToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return firstOperand / secondOperand;
  }
}
