package se.lovebrandefelt.graphingcalculator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultTokenizedExpression implements TokenizedExpression {
  private Queue<Token> tokens;
  private char[] variables;

  DefaultTokenizedExpression(Queue<Token> tokens, char... variables) {
    this.tokens = tokens;
    this.variables = variables;
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  @Override
  public char[] variables() {
    return variables;
  }

  @Override
  public Token[] toArray() {
    return tokens.toArray(new Token[] {});
  }

  @Override
  public String toString() {
    return Arrays.toString(toArray());
  }
}
