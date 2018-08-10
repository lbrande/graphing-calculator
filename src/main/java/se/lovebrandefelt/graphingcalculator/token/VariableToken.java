package se.lovebrandefelt.graphingcalculator.token;

public class VariableToken extends Token {
  private char variable;

  public VariableToken(char variable) {
    this.variable = variable;
  }

  @Override
  public boolean isVariable() {
    return true;
  }

  @Override
  public String toString() {
    return String.valueOf(variable);
  }
}
