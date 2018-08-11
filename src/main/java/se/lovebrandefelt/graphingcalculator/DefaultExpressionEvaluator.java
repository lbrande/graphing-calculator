package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import se.lovebrandefelt.graphingcalculator.token.Token;
import se.lovebrandefelt.graphingcalculator.token.VariableToken;

class DefaultExpressionEvaluator implements ExpressionEvaluator {
  private static final String ILLEGAL_POSTFIX_EXPRESSION_ERROR_MESSAGE =
      "%s is not a legal postfix expression.";
  private static final String INCORRECT_NUMBER_OF_ARGS_SUPPLIED_ERROR_MESSAGE =
      "%d arguments was supplied but exactly %d was needed";
  private static final String NOT_VARIABLE_ERROR_MESSAGE = "%c is not a variable.";

  private char[] variables;
  private double[] args;

  @Override
  public double evaluate(TokenizedExpression postfixExpression, double... args) {
    if (args.length != postfixExpression.variables().length) {
      throw new IllegalArgumentException(
          String.format(
              INCORRECT_NUMBER_OF_ARGS_SUPPLIED_ERROR_MESSAGE,
              args.length,
              postfixExpression.variables().length));
    }

    variables = postfixExpression.variables();
    this.args = args;
    var evaluationStack = new ArrayDeque<Double>();

    for (Token token : postfixExpression) {
      if (token.isNumeric()) {
        evaluationStack.addFirst(token.getNumericValue());
      } else if (token.isVariable()) {
        evaluationStack.addFirst(valueOfVariable((VariableToken) token));
      } else if (token.isBinaryOperator()) {
        if (evaluationStack.size() >= 2) {
          var secondArg = evaluationStack.removeFirst();
          var firstArg = evaluationStack.removeFirst();
          evaluationStack.addFirst(token.evaluate(firstArg, secondArg));
        } else {
          throw new IllegalArgumentException(
              String.format(
                  ILLEGAL_POSTFIX_EXPRESSION_ERROR_MESSAGE, postfixExpression.toString()));
        }
      }
    }

    if (evaluationStack.size() == 1) {
      return evaluationStack.getFirst();
    } else {
      throw new IllegalArgumentException(
          String.format(ILLEGAL_POSTFIX_EXPRESSION_ERROR_MESSAGE, postfixExpression.toString()));
    }
  }

  private double valueOfVariable(VariableToken variable) {
    for (int i = 0; i < variables.length; i++) {
      if (variable.getVariableChar() == variables[i]) {
        return args[i];
      }
    }
    throw new IllegalArgumentException(
        String.format(NOT_VARIABLE_ERROR_MESSAGE, variable.getVariableChar()));
  }
}
