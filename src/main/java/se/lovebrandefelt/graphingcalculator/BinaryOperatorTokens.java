package se.lovebrandefelt.graphingcalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

abstract class BinaryOperatorTokens {
  private static final Map<Character, Supplier<Token>> BINARY_OPERATORS = new HashMap<>();

  private static final String NOT_BINARY_OPERATOR_ERROR_MESSAGE = " is not a binary operator.";

  static {
    BINARY_OPERATORS.put('+', AddToken::new);
    BINARY_OPERATORS.put('-', SubToken::new);
    BINARY_OPERATORS.put('*', MulToken::new);
    BINARY_OPERATORS.put('/', DivToken::new);
    BINARY_OPERATORS.put('^', PowToken::new);
  }

  static boolean isBinaryOperator(char operator) {
    return BINARY_OPERATORS.containsKey(operator);
  }

  static Token newToken(char operator) {
    if (BINARY_OPERATORS.containsKey(operator)) {
      return BINARY_OPERATORS.get(operator).get();
    } else {
      throw new IllegalArgumentException(operator + NOT_BINARY_OPERATOR_ERROR_MESSAGE);
    }
  }
}
