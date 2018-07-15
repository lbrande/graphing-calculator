package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayList;
import se.lovebrandefelt.graphingcalculator.token.Token;

abstract class TestUtils {
  static Token[] expressionToArray(TokenizedExpression tokenizedExpression) {
    var tokens = new ArrayList<Token>();
    while (tokenizedExpression.hasNext()) {
      tokens.add(tokenizedExpression.next());
    }
    return tokens.toArray(new Token[] {});
  }
}
