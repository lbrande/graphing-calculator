package se.lovebrandefelt.graphingcalculator.token;

public class RightParenToken extends Token {
  @Override
  public boolean isRightParen() {
    return true;
  }

  @Override
  public String toString() {
    return ")";
  }
}
