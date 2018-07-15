package se.lovebrandefelt.graphingcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import se.lovebrandefelt.graphingcalculator.token.AddToken;
import se.lovebrandefelt.graphingcalculator.token.DivToken;
import se.lovebrandefelt.graphingcalculator.token.DoubleToken;
import se.lovebrandefelt.graphingcalculator.token.MulToken;
import se.lovebrandefelt.graphingcalculator.token.PowToken;
import se.lovebrandefelt.graphingcalculator.token.SubToken;

class DefaultExpressionConverterTest {
  private ExpressionParser parser;
  private ExpressionConverter converter;

  DefaultExpressionConverterTest() {
    parser = new DefaultExpressionParser();
    converter = new DefaultExpressionConverter();
  }

  @TestFactory
  DynamicTest[] infixToPostfix_binaryOperation_returnsThatOperationInPostfix() {
    return new DynamicTest[] {
      newConverterTest(
          new DefaultTokenizedExpression(new DoubleToken(5), new DoubleToken(7), new AddToken()),
          "5 + 7"),
      newConverterTest(
          new DefaultTokenizedExpression(new DoubleToken(15), new DoubleToken(3), new SubToken()),
          "15 - 3"),
      newConverterTest(
          new DefaultTokenizedExpression(new DoubleToken(3), new DoubleToken(4), new MulToken()),
          "3 * 4"),
      newConverterTest(
          new DefaultTokenizedExpression(new DoubleToken(12), new DoubleToken(4), new DivToken()),
          "12 / 4"),
      newConverterTest(
          new DefaultTokenizedExpression(new DoubleToken(4), new DoubleToken(2), new PowToken()),
          "4 ^ 2"),
    };
  }

  private DynamicTest newConverterTest(TokenizedExpression expected, String expression) {
    return DynamicTest.dynamicTest(
        expression,
        () ->
            Assertions.assertArrayEquals(
                TestUtils.expressionToArray(expected),
                TestUtils.expressionToArray(converter.infixToPostfix(parser.parse(expression)))));
  }
}
