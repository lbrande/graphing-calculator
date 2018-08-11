package se.lovebrandefelt.graphingcalculator;

public class Function {
  private static ExpressionParser parser = new DefaultExpressionParser();
  private static ExpressionConverter converter = new DefaultExpressionConverter();
  private static ExpressionEvaluator evaluator = new DefaultExpressionEvaluator();

  private TokenizedExpression postfixExpression;

  public Function(String expression, char... variables) {
    postfixExpression = converter.infixToPostfix(parser.parse(expression, variables));
  }

  public double evaluate(double... args) {
    return evaluator.evaluate(postfixExpression, args);
  }
}
