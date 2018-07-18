package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import se.lovebrandefelt.graphingcalculator.token.Associativity;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultExpressionConverter implements ExpressionConverter {
  private static final String NO_TOKEN_TYPE_ERROR_MESSAGE =
      "Token is not numeric nor a binary operator.";

  @Override
  public TokenizedExpression infixToPostfix(TokenizedExpression infixExpression) {
    var tokens = new ArrayDeque<Token>();
    var operatorStack = new ArrayDeque<Token>();

    for (Token token : infixExpression) {
      if (token.isNumeric()) {
        tokens.add(token);
      } else if (token.isBinaryOperator()) {
        while (!operatorStack.isEmpty()
            && (operatorStack.getFirst().getPrecedence() > token.getPrecedence()
                || (operatorStack.getFirst().getPrecedence() == token.getPrecedence()
                    && operatorStack.getFirst().getAssociativity() == Associativity.LEFT))) {
          tokens.add(operatorStack.removeFirst());
        }
        operatorStack.addFirst(token);
      } else {
        throw new IllegalArgumentException(NO_TOKEN_TYPE_ERROR_MESSAGE);
      }
    }

    tokens.addAll(operatorStack);

    return new DefaultTokenizedExpression(tokens);
  }
}
