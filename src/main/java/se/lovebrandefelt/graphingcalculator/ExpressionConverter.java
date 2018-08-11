package se.lovebrandefelt.graphingcalculator;

interface ExpressionConverter {
  TokenizedExpression infixToPostfix(TokenizedExpression infixExpression);
}
