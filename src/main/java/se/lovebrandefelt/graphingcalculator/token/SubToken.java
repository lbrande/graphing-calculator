package se.lovebrandefelt.graphingcalculator.token;

public class SubToken extends Token {
  @Override
  public boolean isBinaryOperator() {
    return true;
  }

  @Override
  public double evaluate(double firstOperand, double secondOperand) {
    return firstOperand - secondOperand;
  }

  @Override
  public int getPrecedence() {
    return 0;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.LEFT;
  }

  @Override
  public String toString() {
    return "-";
  }
}
