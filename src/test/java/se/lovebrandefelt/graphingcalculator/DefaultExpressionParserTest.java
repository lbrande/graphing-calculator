package se.lovebrandefelt.graphingcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import se.lovebrandefelt.graphingcalculator.token.AddToken;
import se.lovebrandefelt.graphingcalculator.token.DivToken;
import se.lovebrandefelt.graphingcalculator.token.DoubleToken;
import se.lovebrandefelt.graphingcalculator.token.LeftParenToken;
import se.lovebrandefelt.graphingcalculator.token.MulToken;
import se.lovebrandefelt.graphingcalculator.token.PowToken;
import se.lovebrandefelt.graphingcalculator.token.RightParenToken;
import se.lovebrandefelt.graphingcalculator.token.SubToken;

class DefaultExpressionParserTest {
  private ExpressionParser parser;

  DefaultExpressionParserTest() {
    parser = new DefaultExpressionParser();
  }

  @TestFactory
  DynamicTest[] parse_number_returnsThatNumber() {
    return new DynamicTest[] {
      newParseTest(new DefaultTokenizedExpression(new DoubleToken(100)), "100"),
      newParseTest(new DefaultTokenizedExpression(new DoubleToken(1.25)), "1.25"),
      newParseTest(new DefaultTokenizedExpression(new DoubleToken(-3.33)), "-3.33")
    };
  }

  @TestFactory
  DynamicTest[] parse_binaryOperation_returnsThatOperation() {
    return new DynamicTest[] {
      newParseTest(
          new DefaultTokenizedExpression(new DoubleToken(5), new AddToken(), new DoubleToken(7)),
          "5 + 7"),
      newParseTest(
          new DefaultTokenizedExpression(new DoubleToken(15), new SubToken(), new DoubleToken(3)),
          "15 - 3"),
      newParseTest(
          new DefaultTokenizedExpression(new DoubleToken(3), new MulToken(), new DoubleToken(4)),
          "3 * 4"),
      newParseTest(
          new DefaultTokenizedExpression(new DoubleToken(12), new DivToken(), new DoubleToken(4)),
          "12 / 4"),
      newParseTest(
          new DefaultTokenizedExpression(new DoubleToken(4), new PowToken(), new DoubleToken(2)),
          "4 ^ 2"),
    };
  }

  @TestFactory
  DynamicTest[] parse_expressionWithBinaryOperations_returnsThatExpression() {
    return new DynamicTest[] {
      newParseTest(
          new DefaultTokenizedExpression(
              new DoubleToken(5),
              new AddToken(),
              new DoubleToken(7),
              new MulToken(),
              new DoubleToken(3)),
          "5 + 7 * 3"),
      newParseTest(
          new DefaultTokenizedExpression(
              new DoubleToken(3),
              new SubToken(),
              new DoubleToken(4),
              new PowToken(),
              new DoubleToken(2),
              new DivToken(),
              new DoubleToken(4)),
          "3 - 4 ^ 2 / 4")
    };
  }

  @TestFactory
  DynamicTest[] parse_expressionWithParenthesis_returnsThatExpression() {
    return new DynamicTest[] {
      newParseTest(
          new DefaultTokenizedExpression(
              new DoubleToken(5),
              new AddToken(),
              new LeftParenToken(),
              new DoubleToken(7),
              new SubToken(),
              new DoubleToken(3),
              new RightParenToken()),
          "5 + (7 - 3)"),
      newParseTest(
          new DefaultTokenizedExpression(
              new DoubleToken(3),
              new MulToken(),
              new LeftParenToken(),
              new LeftParenToken(),
              new DoubleToken(4),
              new DivToken(),
              new DoubleToken(4),
              new RightParenToken(),
              new PowToken(),
              new DoubleToken(2),
              new RightParenToken()),
          "3 * ((4 / 4) ^ 2)")
    };
  }

  @Test
  void parse_consecutiveNumbers_throwsException() {
    var expression = "0.11.1";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @Test
  void parse_consecutiveBinaryOperators_throwsException() {
    var expression = "1 + + 1";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @Test
  void parse_binaryOperatorFirst_throwsException() {
    var expression = "- 2";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @Test
  void parse_binaryOperatorLast_throwsException() {
    var expression = "3 *";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @Test
  void parse_openParenthesis_throwsException() {
    var expression = "4 / (4 ^ 4";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @Test
  void parse_rightParenthesisBeforeLeftParenthesis_throwsException() {
    var expression = "5 + 5 - 5)";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  private DynamicTest newParseTest(TokenizedExpression expected, String expression) {
    return DynamicTest.dynamicTest(
        expression,
        () -> Assertions.assertArrayEquals(expected.toArray(), parser.parse(expression).toArray()));
  }
}
