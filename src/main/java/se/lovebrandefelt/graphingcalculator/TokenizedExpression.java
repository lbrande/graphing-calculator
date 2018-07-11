package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class TokenizedExpression {
  private Queue<Token> tokens;

  TokenizedExpression(Queue<Token> tokens) {
    this.tokens = tokens;
  }

  TokenizedExpression(Token... tokens) {
    this.tokens = new ArrayDeque<>(Arrays.asList(tokens));
  }

  Token next() {
    return tokens.remove();
  }

  boolean hasNext() {
    return !tokens.isEmpty();
  }
}
