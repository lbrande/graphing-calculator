package se.lovebrandefelt.graphingcalculator;

import java.util.ArrayDeque;
import se.lovebrandefelt.graphingcalculator.token.Associativity;
import se.lovebrandefelt.graphingcalculator.token.Token;

class DefaultExpressionConverter implements ExpressionConverter {
  private static final String NO_TOKEN_TYPE_ERROR_MESSAGE = " has no type.";
  private static final String MISMATCHED_PARENS_ERROR_MESSAGE = " contains mismatched parenthesis.";

  @Override
  public TokenizedExpression infixToPostfix(TokenizedExpression infixExpression) {
    var tokens = new ArrayDeque<Token>();
    var operatorStack = new ArrayDeque<Token>();

    for (Token token : infixExpression) {
      if (token.isNumeric() || token.isVariable()) {
        tokens.add(token);
      } else if (token.isBinaryOperator()) {
        while (!operatorStack.isEmpty()
            && !operatorStack.getFirst().isLeftParen()
            && (operatorStack.getFirst().getPrecedence() > token.getPrecedence()
                || (operatorStack.getFirst().getPrecedence() == token.getPrecedence()
                    && operatorStack.getFirst().getAssociativity() == Associativity.LEFT))) {
          tokens.add(operatorStack.removeFirst());
        }
        operatorStack.addFirst(token);
      } else if (token.isLeftParen()) {
        operatorStack.addFirst(token);
      } else if (token.isRightParen()) {
        while (!operatorStack.isEmpty() && !operatorStack.getFirst().isLeftParen()) {
          tokens.add(operatorStack.removeFirst());
        }
        if (!operatorStack.isEmpty() && operatorStack.getFirst().isLeftParen()) {
          operatorStack.removeFirst();
        } else {
          throw new IllegalArgumentException(infixExpression + MISMATCHED_PARENS_ERROR_MESSAGE);
        }
      } else {
        throw new IllegalArgumentException(token + NO_TOKEN_TYPE_ERROR_MESSAGE);
      }
    }

    tokens.addAll(operatorStack);

    return new TokenizedExpression(tokens, infixExpression.variables());
  }
}
