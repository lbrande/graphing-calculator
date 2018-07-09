package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DefaultExpressionParser implements ExpressionParser {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile(NUMERIC_VALUE_REGEX);
  private static final Map<Character, Supplier<Token>> BINARY_OPERATORS = new HashMap<>();
  private static final String ILLEGAL_EXPRESSION_ERROR_MESSAGE = " is not a legal expression.";

  static {
    BINARY_OPERATORS.put('+', PlusToken::new);
    BINARY_OPERATORS.put('-', MinusToken::new);
    BINARY_OPERATORS.put('*', TimesToken::new);
    BINARY_OPERATORS.put('/', DivisionToken::new);
    BINARY_OPERATORS.put('^', PowerToken::new);
  }

  private String expression;
  private Matcher numericValueMatcher;
  private Queue<Token> tokens;
  private int expressionIndex;
  private char currentChar;

  @Override
  public TokenizedExpression parse(String expression) {
    initParsing(expression);

    while (expressionIndex < expression.length()) {
      tokens.add(parseNextToken());
      if (tokens.element().isNumeric()) {
        expressionIndex = numericValueMatcher.end();
      } else {
        expressionIndex++;
      }
    }

    return new TokenizedExpression(tokens);
  }

  private void initParsing(String expression) {
    this.expression = expression;
    numericValueMatcher = NUMERIC_VALUE_PATTERN.matcher(expression);
    tokens = new ArrayDeque<>();
    expressionIndex = 0;
  }

  private Token parseNextToken() {
    currentChar = expression.charAt(expressionIndex);
    if (isOnBinaryOperator()) {
      return BINARY_OPERATORS.get(currentChar).get();
    } else if (isOnNumericValue()) {
      return new DoubleToken(
          Double.parseDouble(
              expression.substring(numericValueMatcher.start(), numericValueMatcher.end())));
    } else {
      throw new IllegalArgumentException(expression + ILLEGAL_EXPRESSION_ERROR_MESSAGE);
    }
  }

  private boolean isOnNumericValue() {
    return numericValueMatcher.find(expressionIndex)
        && numericValueMatcher.start() == expressionIndex
        && (tokens.isEmpty() || tokens.element().isBinaryOperator());
  }

  private boolean isOnBinaryOperator() {
    return BINARY_OPERATORS.containsKey(currentChar)
        && !tokens.isEmpty()
        && tokens.element().isNumeric();
  }
}
