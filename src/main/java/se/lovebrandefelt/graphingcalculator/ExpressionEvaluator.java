package se.lovebrandefelt.graphingcalculator;

public interface ExpressionEvaluator {
  double evaluate(TokenizedExpression postfixExpression, double... variableValues);
}
