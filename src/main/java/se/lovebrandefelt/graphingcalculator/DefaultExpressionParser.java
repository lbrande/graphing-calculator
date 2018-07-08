package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayList;
import java.util.List;

class DefaultExpressionParser implements ExpressionParser {
  private String expression;
  private int expressionIndex;
  private List<Token> tokens;

  @Override
  public TokenizedExpression parse(String expression) throws ExpressionParsingException {
    this.expression = expression;
    expressionIndex = 0;
    tokens = new ArrayList<>();

    do {
      if (onStartOfNumericValue()) {
        tokens.add(new DoubleToken(parseNumericValue()));
      }
      expressionIndex++;
    } while (expressionIndex < expression.length());
    return new TokenizedExpression(tokens);
  }

  private boolean onStartOfNumericValue() {
    char currentChar = expression.charAt(expressionIndex);
    return Character.isDigit(currentChar) || currentChar == '-';
  }

  private double parseNumericValue() throws ExpressionParsingException {
    int startOfNumericValue = expressionIndex;
    findEndOfNumericValue();
    int endOfNumericValue = expressionIndex;

    String toParse = expression.substring(startOfNumericValue, endOfNumericValue);
    try {
      return Double.parseDouble(toParse);
    } catch (NumberFormatException e) {
      throw new ExpressionParsingException(toParse + " could not be parsed as a numeric value.");
    }
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
