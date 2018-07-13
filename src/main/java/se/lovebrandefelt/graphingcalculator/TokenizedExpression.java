package se.lovebrandefelt.graphingcalculator;

import se.lovebrandefelt.graphingcalculator.token.Token;

public interface TokenizedExpression {
  Token next();

  boolean hasNext();
}
