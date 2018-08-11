package se.lovebrandefelt.graphingcalculator;

interface ExpressionEvaluator {
  double evaluate(TokenizedExpression postfixExpression, double... variableValues);
}
