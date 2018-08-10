package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import se.lovebrandefelt.graphingcalculator.token.Token;

abstract class TestUtils {
  static TokenizedExpression newExpression(Token... tokens) {
    return new DefaultTokenizedExpression(new ArrayDeque<>(Arrays.asList(tokens)));
  }
}
