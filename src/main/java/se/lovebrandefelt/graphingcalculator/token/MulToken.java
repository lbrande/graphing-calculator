package se.lovebrandefelt.graphingcalculator.token;

public class MulToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return firstOperand * secondOperand;
  }
}