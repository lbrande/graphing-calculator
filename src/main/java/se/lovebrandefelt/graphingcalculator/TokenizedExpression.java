package se.lovebrandefelt.graphingcalculator;

import se.lovebrandefelt.graphingcalculator.token.Token;

interface TokenizedExpression extends Iterable<Token> {
  char[] variables();

  Token[] toArray();
}
