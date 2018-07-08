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
  void parsingOfNumericValueIsIdenticalToParseDouble(String numberString) {
    TokenizedExpression tokenizedExpression = parser.parse(numberString);
    Assertions.assertEquals(1, tokenizedExpression.tokenCount());

    Token token = tokenizedExpression.tokenAt(0);
    Assertions.assertTrue(token.isNumeric());
    Assertions.assertEquals(Double.parseDouble(numberString), token.getNumericValue());
  }

  @Test
  void parsingOfIncorrectNumericValueThrowsExpressionParsingException() {
    String toParse = "5.5.5";
    Executable parse = () -> parser.parse(toParse);
    Assertions.assertThrows(ExpressionParsingException.class, parse);
  }
}
