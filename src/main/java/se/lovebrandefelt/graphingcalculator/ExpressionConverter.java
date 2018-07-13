package se.lovebrandefelt.graphingcalculator;

public interface ExpressionConverter {
  TokenizedExpression infixToPostfix(TokenizedExpression infixExpression);
}
