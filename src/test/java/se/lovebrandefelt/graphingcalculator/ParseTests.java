package se.lovebrandefelt.graphingcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParseTests {
  private ExpressionParser parser;

  ParseTests() {
    parser = new DefaultExpressionParser();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10000000000", "-10000000000", "3.333", "-3.333"})
  void parsingOfANumericValueIsIdenticalToParseDouble(String numberString) {
    Token token = firstToken(numberString);
    Assertions.assertTrue(token.isNumeric());
    Assertions.assertEquals(Double.parseDouble(numberString), token.getNumericValue());
  }

  @Test
  void parsingOfTwoConsecutiveNumbersThrowsIllegalArgumentException() {
    String toParse = "3.3333.333";
    Executable parse = () -> parser.parse(toParse);
    Assertions.assertThrows(IllegalArgumentException.class, parse);
  }

  @Test
  void parsingOfPlusReturnsAPlusToken() {
    String toParse = "+";
    Token token = firstToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof PlusToken);
  }

  @Test
  void parsingOfMinusReturnsAMinusToken() {
    String toParse = "-";
    Token token = firstToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof MinusToken);
  }

  @Test
  void parsingOfTimesReturnsATimesToken() {
    String toParse = "*";
    Token token = firstToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof TimesToken);
  }

  @Test
  void parsingOfDivisionReturnsADivisionToken() {
    String toParse = "/";
    Token token = firstToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof DivisionToken);
  }

  @Test
  void parsingOfPowerReturnsAPowerToken() {
    String toParse = "^";
    Token token = firstToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof PowerToken);
  }

  private Token firstToken(String toParse) {
    TokenizedExpression tokenizedExpression = parser.parse(toParse);
    Assertions.assertTrue(tokenizedExpression.hasNext());
    return tokenizedExpression.next();
  }
}
