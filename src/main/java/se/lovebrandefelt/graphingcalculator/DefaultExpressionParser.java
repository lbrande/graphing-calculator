package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.lovebrandefelt.graphingcalculator.token.BinaryOperatorTokens;
import se.lovebrandefelt.graphingcalculator.token.DoubleToken;
import se.lovebrandefelt.graphingcalculator.token.LeftParenToken;
import se.lovebrandefelt.graphingcalculator.token.RightParenToken;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultExpressionParser implements ExpressionParser {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile(NUMERIC_VALUE_REGEX);

  private static final String ILLEGAL_EXPRESSION_ERROR_MESSAGE = " is not a legal expression.";
  private static final String NOT_PAREN_ERROR_MESSAGE = " is not a parenthesis.";

  private String expression;
  private Matcher numericValueMatcher;
  private ArrayDeque<Token> tokens;
  private int expressionIndex;
  private int openParens;

  @Override
  public TokenizedExpression parse(String expression) {
    initParsing(expression);

    while (expressionIndex < expression.length()) {
      tokens.addLast(parseNextToken());
    }

    if (isExpressionIllegal()) {
      throw newIllegalExpressionException();
    }

    return new DefaultTokenizedExpression(tokens);
  }

  private void initParsing(String expression) {
    this.expression = expression.trim();
    numericValueMatcher = NUMERIC_VALUE_PATTERN.matcher(this.expression);
    tokens = new ArrayDeque<>();
    expressionIndex = 0;
    openParens = 0;
  }

  private Token parseNextToken() {
    findNextToken();
    if (canParseParen()) {
      return parseParen();
    } else if (canParseBinaryOperator()) {
      return parseBinaryOperator();
    } else if (canParseNumericValue()) {
      return parseNumericValue();
    } else {
      throw newIllegalExpressionException();
    }
  }

  private boolean isExpressionIllegal() {
    return tokens.getLast().isBinaryOperator() || openParens > 0;
  }

  private IllegalArgumentException newIllegalExpressionException() {
    return new IllegalArgumentException(expression + ILLEGAL_EXPRESSION_ERROR_MESSAGE);
  }

  private void findNextToken() {
    while (expressionIndex < expression.length()
        && Character.isWhitespace(expression.charAt(expressionIndex))) {
      expressionIndex++;
    }
  }

  private boolean canParseNumericValue() {
    return (tokens.isEmpty()
            || tokens.getLast().isBinaryOperator()
            || tokens.getLast() instanceof LeftParenToken)
        && numericValueMatcher.find(expressionIndex)
        && numericValueMatcher.start() == expressionIndex;
  }

  private Token parseNumericValue() {
    var token =
        new DoubleToken(
            Double.parseDouble(
                expression.substring(numericValueMatcher.start(), numericValueMatcher.end())));

    expressionIndex = numericValueMatcher.end();
    return token;
  }

  private boolean canParseBinaryOperator() {
    var currentChar = expression.charAt(expressionIndex);

    return !tokens.isEmpty()
        && (tokens.getLast().isNumeric() || tokens.getLast() instanceof RightParenToken)
        && BinaryOperatorTokens.isBinaryOperator(currentChar);
  }

  private Token parseBinaryOperator() {
    var currentChar = expression.charAt(expressionIndex);
    var token = BinaryOperatorTokens.newToken(currentChar);

    expressionIndex++;
    return token;
  }

  private boolean canParseParen() {
    var currentChar = expression.charAt(expressionIndex);

    return currentChar == '('
        || (currentChar == ')'
            && !tokens.isEmpty()
            && !(tokens.getLast() instanceof LeftParenToken)
            && !tokens.getLast().isBinaryOperator()
            && openParens > 0);
  }

  private Token parseParen() {
    var currentChar = expression.charAt(expressionIndex);
    Token token;
    if (currentChar == '(') {
      token = new LeftParenToken();
      openParens++;
    } else if (currentChar == ')') {
      token = new RightParenToken();
      openParens--;
    } else {
      throw new IllegalArgumentException(currentChar + NOT_PAREN_ERROR_MESSAGE);
    }

    expressionIndex++;
    return token;
  }
}
