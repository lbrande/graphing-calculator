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
import se.lovebrandefelt.graphingcalculator.token.VariableToken;

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
          TestUtils.newExpression(new DoubleToken(5), new DoubleToken(7), new AddToken()), "5 + 7"),
      newConverterTest(
          TestUtils.newExpression(new DoubleToken(15), new DoubleToken(3), new SubToken()),
          "15 - 3"),
      newConverterTest(
          TestUtils.newExpression(new DoubleToken(3), new DoubleToken(4), new MulToken()), "3 * 4"),
      newConverterTest(
          TestUtils.newExpression(new DoubleToken(12), new DoubleToken(4), new DivToken()),
          "12 / 4"),
      newConverterTest(
          TestUtils.newExpression(new DoubleToken(4), new DoubleToken(2), new PowToken()), "4 ^ 2"),
    };
  }

  @TestFactory
  DynamicTest[] infixToPostfix_expressionWithBinaryOperations_returnsThatExpressionInPostfix() {
    return new DynamicTest[] {
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(5),
              new DoubleToken(7),
              new DoubleToken(3),
              new MulToken(),
              new AddToken()),
          "5 + 7 * 3"),
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(3),
              new DoubleToken(4),
              new DoubleToken(2),
              new PowToken(),
              new DoubleToken(4),
              new DivToken(),
              new SubToken()),
          "3 - 4 ^ 2 / 4")
    };
  }

  @TestFactory
  DynamicTest[] infixToPostfix_expressionWithParenthesis_returnsThatExpressionInPostfix() {
    return new DynamicTest[] {
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(5),
              new DoubleToken(7),
              new DoubleToken(3),
              new SubToken(),
              new AddToken()),
          "5 + (7 - 3)"),
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(3),
              new DoubleToken(4),
              new DoubleToken(4),
              new DivToken(),
              new DoubleToken(2),
              new PowToken(),
              new MulToken()),
          "3 * ((4 / 4) ^ 2)")
    };
  }

  @TestFactory
  DynamicTest[] infixToPostfix_expressionWithVariables_returnsThatExpressionInPostfix() {
    return new DynamicTest[] {
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(5),
              new VariableToken('x'),
              new DoubleToken(3),
              new SubToken(),
              new AddToken()),
          "5 + (x - 3)",
          'x'),
      newConverterTest(
          TestUtils.newExpression(
              new DoubleToken(3),
              new VariableToken('y'),
              new DoubleToken(4),
              new DivToken(),
              new VariableToken('y'),
              new PowToken(),
              new MulToken()),
          "3 * ((y / 4) ^ y)",
          'y')
    };
  }

  private DynamicTest newConverterTest(
      TokenizedExpression expected, String expression, char... variables) {
    return DynamicTest.dynamicTest(
        expression,
        () ->
            Assertions.assertArrayEquals(
                expected.toArray(),
                converter.infixToPostfix(parser.parse(expression, variables)).toArray()));
  }
}
