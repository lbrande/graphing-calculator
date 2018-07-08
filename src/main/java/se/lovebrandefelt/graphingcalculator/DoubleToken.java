package se.lovebrandefelt.graphingcalculator;

class DoubleToken extends Token {
  private double value;

  DoubleToken(double value) {
    this.value = value;
  }

  @Override
  public boolean isNumeric() {
    return true;
  }

  @Override
  public double getNumericValue() {
    return value;
  }
}
