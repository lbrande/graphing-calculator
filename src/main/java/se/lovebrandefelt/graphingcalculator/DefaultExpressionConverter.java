package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultExpressionConverter implements ExpressionConverter {
  private static final String NO_TOKEN_TYPE_ERROR_MESSAGE =
      "Token is not numeric nor a binary operator.";

  @Override
  public TokenizedExpression infixToPostfix(TokenizedExpression infixExpression) {
    Queue<Token> tokens = new ArrayDeque<>();
    Deque<Token> operators = new ArrayDeque<>();

    for (Token token : infixExpression) {
      if (token.isNumeric()) {
        tokens.add(token);
      } else if (token.isBinaryOperator()) {
        operators.addFirst(token);
      } else {
        throw new IllegalArgumentException(NO_TOKEN_TYPE_ERROR_MESSAGE);
      }
    }

    tokens.addAll(operators);

    return new DefaultTokenizedExpression(tokens);
  }
}
