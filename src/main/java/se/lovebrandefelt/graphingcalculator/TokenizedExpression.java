package se.lovebrandefelt.graphingcalculator;

import java.util.List;

class TokenizedExpression {
  private List<Token> tokens;

  TokenizedExpression(List<Token> tokens) {
    this.tokens = tokens;
  }

  Token tokenAt(int i) {
    return tokens.get(i);
  }

  int tokenCount() {
    return tokens.size();
  }
}
