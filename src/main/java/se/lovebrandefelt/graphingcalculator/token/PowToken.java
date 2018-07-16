package se.lovebrandefelt.graphingcalculator.token;

public class PowToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return Math.pow(firstOperand, secondOperand);
  }

  @Override
  public String toString() {
    return "^";
  }
}
