package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class ParseTests {
  private ExpressionParser parser;

  ParseTests() {
    parser = new DefaultExpressionParser();
  }

  @TestFactory
  DynamicTest[] parsingOfANumberReturnsCorrespondingDouble() {
    return new DynamicTest[] {
      newParseTest(new TokenizedExpression(new DoubleToken(100)), "100"),
      newParseTest(new TokenizedExpression(new DoubleToken(1.25)), "1.25"),
      newParseTest(new TokenizedExpression(new DoubleToken(-3.33)), "-3.33")
    };
  }

  @Test
  void parsingOfTwoConsecutiveNumbersThrowsIllegalArgumentException() {
    String expression = "0.11.1";
    Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(expression));
  }

  @TestFactory
  DynamicTest[] parsingOfABinaryOperationReturnsThatOperation() {
    return new DynamicTest[] {
      newParseTest(
          new TokenizedExpression(new DoubleToken(5), new AddToken(), new DoubleToken(7)), "5 + 7"),
      newParseTest(
          new TokenizedExpression(new DoubleToken(15), new SubToken(), new DoubleToken(3)),
          "15 - 3"),
      newParseTest(
          new TokenizedExpression(new DoubleToken(3), new MulToken(), new DoubleToken(4)), "3 * 4"),
      newParseTest(
          new TokenizedExpression(new DoubleToken(12), new DivToken(), new DoubleToken(4)),
          "12 / 4"),
      newParseTest(
          new TokenizedExpression(new DoubleToken(4), new PowToken(), new DoubleToken(2)), "4 ^ 2"),
    };
  }

  private DynamicTest newParseTest(TokenizedExpression expected, String expression) {
    return DynamicTest.dynamicTest(
        expression,
        () ->
            Assertions.assertArrayEquals(
                expressionToArray(expected), expressionToArray(parser.parse(expression))));
  }

  private Token[] expressionToArray(TokenizedExpression tokenizedExpression) {
    List<Token> tokens = new ArrayList<>();
    while (tokenizedExpression.hasNext()) {
      tokens.add(tokenizedExpression.next());
    }
    return tokens.toArray(new Token[] {});
  }
}
