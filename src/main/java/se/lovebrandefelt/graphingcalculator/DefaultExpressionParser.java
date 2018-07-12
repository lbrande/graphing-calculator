package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DefaultExpressionParser implements ExpressionParser {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile(NUMERIC_VALUE_REGEX);

  private static final String ILLEGAL_EXPRESSION_ERROR_MESSAGE = " is not a legal expression.";

  private String expression;
  private Matcher numericValueMatcher;
  private ArrayDeque<Token> tokens;
  private int expressionIndex;

  @Override
  public TokenizedExpression parse(String expression) {
    initParsing(expression);

    while (expressionIndex < expression.length()) {
      tokens.addLast(parseNextToken());
    }

    return new TokenizedExpression(tokens);
  }

  private void initParsing(String expression) {
    this.expression = expression.trim();
    numericValueMatcher = NUMERIC_VALUE_PATTERN.matcher(this.expression);
    tokens = new ArrayDeque<>();
    expressionIndex = 0;
  }

  private Token parseNextToken() {
    findNextToken();
    if (isOnBinaryOperator()) {
      return parseBinaryOperator();
    } else if (isOnNumericValue()) {
      return parseNumericValue();
    } else {
      throw new IllegalArgumentException(expression + ILLEGAL_EXPRESSION_ERROR_MESSAGE);
    }
  }

  private void findNextToken() {
    while (expressionIndex < expression.length()
        && Character.isWhitespace(expression.charAt(expressionIndex))) {
      expressionIndex++;
    }
  }

  private boolean isOnNumericValue() {
    return numericValueMatcher.find(expressionIndex)
        && numericValueMatcher.start() == expressionIndex
        && (tokens.isEmpty() || tokens.getLast().isBinaryOperator());
  }

  private Token parseNumericValue() {
    var token =
        new DoubleToken(
            Double.parseDouble(
                expression.substring(numericValueMatcher.start(), numericValueMatcher.end())));
    expressionIndex = numericValueMatcher.end();
    return token;
  }

  private boolean isOnBinaryOperator() {
    return BinaryOperatorTokens.isBinaryOperator(expression.charAt(expressionIndex))
        && !tokens.isEmpty()
        && tokens.getLast().isNumeric();
  }

  private Token parseBinaryOperator() {
    var token = BinaryOperatorTokens.newToken(expression.charAt(expressionIndex));
    expressionIndex++;
    return token;
  }
}
