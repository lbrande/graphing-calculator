package se.lovebrandefelt.graphingcalculator;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class DefaultExpressionEvaluatorTest {
  private ExpressionParser parser;
  private ExpressionConverter converter;
  private ExpressionEvaluator evaluator;

  DefaultExpressionEvaluatorTest() {
    parser = new DefaultExpressionParser();
    converter = new DefaultExpressionConverter();
    evaluator = new DefaultExpressionEvaluator();
  }

  @TestFactory
  DynamicTest[] evaluate_expressionWithNoVariables_evaluatesToItsValue() {
    return new DynamicTest[] {
      newEvaluatorTest(9, "5 + (7 - 3)"), newEvaluatorTest(3, "3 * ((4 / 4) ^ 2)")
    };
  }

  @TestFactory
  DynamicTest[] evaluate_expressionWithOneVariableAndValues_evaluatesToItsValue() {
    return new DynamicTest[] {
      newEvaluatorTest(9, "5 + (x - 3)", new char[] {'x'}, new double[] {7}),
      newEvaluatorTest(3, "3 * ((y / 4) ^ y)", new char[] {'y'}, new double[] {4})
    };
  }

  private DynamicTest newEvaluatorTest(double expected, String expression) {
    return DynamicTest.dynamicTest(
        expression,
        () ->
            Assertions.assertEquals(
                expected, evaluator.evaluate(converter.infixToPostfix(parser.parse(expression)))));
  }

  private DynamicTest newEvaluatorTest(
      double expected, String expression, char[] variables, double[] args) {
    return DynamicTest.dynamicTest(
        expression + Arrays.toString(variables) + Arrays.toString(args),
        () ->
            Assertions.assertEquals(
                expected,
                evaluator.evaluate(
                    converter.infixToPostfix(parser.parse(expression, variables)), args)));
  }
}
