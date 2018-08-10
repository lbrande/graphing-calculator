package se.lovebrandefelt.graphingcalculator.token;

public class VariableToken extends Token {
  private char character;

  public VariableToken(char variable) {
    this.character = variable;
  }

  @Override
  public boolean isVariable() {
    return true;
  }

  @Override
  public char getVariableChar() {
    return character;
  }

  @Override
  public String toString() {
    return String.valueOf(character);
  }
}
