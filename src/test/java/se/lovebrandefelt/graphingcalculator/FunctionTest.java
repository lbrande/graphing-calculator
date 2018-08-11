package se.lovebrandefelt.graphingcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class FunctionTest {
  @TestFactory
  DynamicTest[] evaluate_functionWithNoVariables_evaluatesToItsValue() {
    return new DynamicTest[] {
      newFunctionTest(9, "5 + (7 - 3)"), newFunctionTest(3, "3 * ((4 / 4) ^ 2)")
    };
  }

  @TestFactory
  DynamicTest[] evaluate_expressionWithOneVariableAndValues_evaluatesToItsValue() {
    return new DynamicTest[] {
      newFunctionTest(9, "5 + (x - 3)", new char[] {'x'}, new double[] {7}),
      newFunctionTest(3, "3 * ((y / 4) ^ y)", new char[] {'y'}, new double[] {4})
    };
  }

  private DynamicTest newFunctionTest(double expected, String expression) {
    return DynamicTest.dynamicTest(
        expression,
        () -> Assertions.assertEquals(expected, expected, new Function(expression).evaluate()));
  }

  private DynamicTest newFunctionTest(
      double expected, String expression, char[] variables, double[] args) {
    return DynamicTest.dynamicTest(
        expression,
        () ->
            Assertions.assertEquals(expected, new Function(expression, variables).evaluate(args)));
  }
}
