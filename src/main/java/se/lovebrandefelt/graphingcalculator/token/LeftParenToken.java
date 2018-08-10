package se.lovebrandefelt.graphingcalculator.token;

public class LeftParenToken extends Token {
  @Override
  public boolean isLeftParen() {
    return true;
  }

  @Override
  public String toString() {
    return "(";
  }
}
