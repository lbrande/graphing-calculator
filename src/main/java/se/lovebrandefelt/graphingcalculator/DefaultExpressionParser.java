package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

class DefaultExpressionParser implements ExpressionParser {
  private static final Map<Character, Supplier<Token>> BINARY_OPERATORS = new HashMap<>();

  static {
    BINARY_OPERATORS.put('+', PlusToken::new);
  }

  private String expression;
  private int expressionIndex;

  @Override
  public TokenizedExpression parse(String expression) throws NumberFormatException {
    this.expression = expression;
    expressionIndex = 0;
    List<Token> tokens = new ArrayList<>();

    do {
      if (onStartOfNumericValue()) {
        tokens.add(new DoubleToken(parseNumericValue()));
      } else if (onBinaryOperator()) {
        tokens.add(createBinaryOperatorToken());
      }
      expressionIndex++;
    } while (expressionIndex < expression.length());
    return new TokenizedExpression(tokens);
  }

  private boolean onStartOfNumericValue() {
    char currentChar = expression.charAt(expressionIndex);
    return Character.isDigit(currentChar) || currentChar == '-';
  }

  private double parseNumericValue() throws NumberFormatException {
    int startOfNumericValue = expressionIndex;
    findEndOfNumericValue();
    int endOfNumericValue = expressionIndex;

    String toParse = expression.substring(startOfNumericValue, endOfNumericValue);
    return Double.parseDouble(toParse);
  }

  private void findEndOfNumericValue() {
    do {
      expressionIndex++;
    } while (expressionIndex < expression.length() && onPartOfNumericValue());
  }

  private boolean onPartOfNumericValue() {
    char currentChar = expression.charAt(expressionIndex);
    return Character.isDigit(currentChar) || currentChar == '.';
  }

  private boolean onBinaryOperator() {
    return BINARY_OPERATORS.containsKey(expression.charAt(expressionIndex));
  }

  private Token createBinaryOperatorToken() {
    return BINARY_OPERATORS.get(expression.charAt(expressionIndex)).get();
  }
}
