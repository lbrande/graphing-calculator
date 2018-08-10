package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultTokenizedExpression implements TokenizedExpression {
  private Queue<Token> tokens;

  DefaultTokenizedExpression(Queue<Token> tokens) {
    this.tokens = tokens;
  }

  DefaultTokenizedExpression(Token... tokens) {
    this.tokens = new ArrayDeque<>(Arrays.asList(tokens));
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  @Override
  public Token[] toArray() {
    return tokens.toArray(new Token[] {});
  }
}
