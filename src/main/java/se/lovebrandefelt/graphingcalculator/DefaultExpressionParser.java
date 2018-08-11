package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.lovebrandefelt.graphingcalculator.token.BinaryOperatorTokens;
import se.lovebrandefelt.graphingcalculator.token.DoubleToken;
import se.lovebrandefelt.graphingcalculator.token.LeftParenToken;
import se.lovebrandefelt.graphingcalculator.token.RightParenToken;
import se.lovebrandefelt.graphingcalculator.token.Token;
import se.lovebrandefelt.graphingcalculator.token.VariableToken;

class DefaultExpressionParser implements ExpressionParser {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile(NUMERIC_VALUE_REGEX);

  private static final String ILLEGAL_EXPRESSION_ERROR_MESSAGE =
      "\"%s\" is not a legal expression.";
  private static final String NOT_VARIABLE_ERROR_MESSAGE = "%c is not a variable.";
  private static final String NOT_PAREN_ERROR_MESSAGE = "%c is not a parenthesis.";

  private String expression;
  private char[] variables;
  private Matcher numericValueMatcher;
  private Deque<Token> tokens;
  private int expressionIndex;
  private int openParens;

  @Override
  public TokenizedExpression parse(String expression, char... variables) {
    initParsing(expression, variables);

    while (expressionIndex < this.expression.length()) {
      tokens.addLast(parseNextToken());
    }

    if (isExpressionIllegal()) {
      throw newIllegalExpressionException();
    }

    return new TokenizedExpression(tokens, variables);
  }

  private void initParsing(String expression, char... variables) {
    this.expression = expression.trim();
    this.variables = variables;
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
    } else if (canParseVariable()) {
      return parseVariable();
    } else {
      throw newIllegalExpressionException();
    }
  }

  private boolean isExpressionIllegal() {
    return (!tokens.isEmpty() && tokens.getLast().isBinaryOperator()) || openParens > 0;
  }

  private IllegalArgumentException newIllegalExpressionException() {
    return new IllegalArgumentException(
        String.format(ILLEGAL_EXPRESSION_ERROR_MESSAGE, expression));
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
            || tokens.getLast().isLeftParen())
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

  private boolean canParseVariable() {
    var currentChar = expression.charAt(expressionIndex);

    return (tokens.isEmpty()
            || tokens.getLast().isBinaryOperator()
            || tokens.getLast().isLeftParen())
        && isVariable(currentChar);
  }

  private boolean isVariable(char character) {
    for (char variable : variables) {
      if (character == variable) {
        return true;
      }
    }
    return false;
  }

  private Token parseVariable() {
    var currentChar = expression.charAt(expressionIndex);
    Token token;
    if (isVariable(currentChar)) {
      token = new VariableToken(currentChar);
    } else {
      throw new IllegalArgumentException(String.format(NOT_VARIABLE_ERROR_MESSAGE, currentChar));
    }

    expressionIndex++;
    return token;
  }

  private boolean canParseBinaryOperator() {
    var currentChar = expression.charAt(expressionIndex);

    return !tokens.isEmpty()
        && (tokens.getLast().isNumeric()
            || tokens.getLast().isVariable()
            || tokens.getLast().isRightParen())
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
            && !(tokens.getLast().isLeftParen())
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
      throw new IllegalArgumentException(String.format(NOT_PAREN_ERROR_MESSAGE, currentChar));
    }

    expressionIndex++;
    return token;
  }
}
