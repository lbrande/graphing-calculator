package se.lovebrandefelt.graphingcalculator.token;

public class AddToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return firstOperand + secondOperand;
  }

  @Override
  public String toString() {
    return "+";
  }
}
