package se.lovebrandefelt.graphingcalculator;

import java.util.Queue;

class TokenizedExpression {
  private Queue<Token> tokens;

  TokenizedExpression(Queue<Token> tokens) {
    this.tokens = tokens;
  }

  Token next() {
    return tokens.remove();
  }

  boolean hasNext() {
    return !tokens.isEmpty();
  }
}
