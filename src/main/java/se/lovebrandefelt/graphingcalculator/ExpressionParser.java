package se.lovebrandefelt.graphingcalculator;

interface ExpressionParser {
  TokenizedExpression parse(String expression, char... variables);
}
