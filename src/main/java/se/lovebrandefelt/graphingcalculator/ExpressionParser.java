package se.lovebrandefelt.graphingcalculator;

public interface ExpressionParser {
  TokenizedExpression parse(String expression, char... variables);
}
