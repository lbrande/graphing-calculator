package se.lovebrandefelt.graphingcalculator;

import se.lovebrandefelt.graphingcalculator.token.Token;

public interface TokenizedExpression extends Iterable<Token> {
  Token next();

  boolean hasNext();
}
