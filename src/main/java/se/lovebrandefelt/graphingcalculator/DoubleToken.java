package se.lovebrandefelt.graphingcalculator;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (DoubleToken) o;
    return Double.compare(that.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
