package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayList;
import java.util.List;

class DefaultExpressionParser implements ExpressionParser {
  private String expression;
  private int expressionIndex;
  private List<Token> tokens;

  @Override
  public TokenizedExpression parse(String expression) {
    this.expression = expression;
    expressionIndex = 0;
    tokens = new ArrayList<>();

    do {
      if (onStartOfNumericValue()) {
        parseNumericValue();
      }
      expressionIndex++;
    } while (expressionIndex < expression.length());
    return new TokenizedExpression(tokens);
  }

  private boolean onStartOfNumericValue() {
    char currentChar = expression.charAt(expressionIndex);
    return Character.isDigit(currentChar) || currentChar == '-';
  }

  private void parseNumericValue() {
    int startOfNumericValue = expressionIndex;
    findEndOfNumericValue();
    int endOfNumericValue = expressionIndex;
    tokens.add(
        new DoubleToken(
            Double.parseDouble(expression.substring(startOfNumericValue, endOfNumericValue))));
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
}
