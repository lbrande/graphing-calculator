package se.lovebrandefelt.graphingcalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import se.lovebrandefelt.graphingcalculator.token.Token;

class TokenizedExpression implements Iterable<Token> {
  private Collection<Token> tokens;
  private char[] variables;

  TokenizedExpression(Collection<Token> tokens, char... variables) {
    this.tokens = tokens;
    this.variables = variables;
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  char[] variables() {
    return variables;
  }

  Token[] toArray() {
    return tokens.toArray(new Token[] {});
  }

  @Override
  public String toString() {
    return Arrays.toString(toArray());
  }
}
