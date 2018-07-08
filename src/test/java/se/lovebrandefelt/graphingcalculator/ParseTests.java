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
    Token token = parseAndAssertSingleToken(numberString);
    Assertions.assertTrue(token.isNumeric());
    Assertions.assertEquals(Double.parseDouble(numberString), token.getNumericValue());
  }

  @Test
  void parsingOfAnIncorrectNumericValueThrowsNumberFormatException() {
    String toParse = "5.5.5";
    Executable parse = () -> parser.parse(toParse);
    Assertions.assertThrows(NumberFormatException.class, parse);
  }

  @Test
  void parsingOfPlusReturnsAPlusToken() {
    String toParse = "+";
    Token token = parseAndAssertSingleToken(toParse);
    Assertions.assertTrue(token.isBinaryOperator());
    Assertions.assertTrue(token instanceof PlusToken);
  }

  private Token parseAndAssertSingleToken(String toParse) {
    TokenizedExpression tokenizedExpression = parser.parse(toParse);
    Assertions.assertEquals(1, tokenizedExpression.tokenCount());
    return tokenizedExpression.tokenAt(0);
  }
}
