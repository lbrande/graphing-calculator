package se.lovebrandefelt.graphingcalculator;

interface Token {
  boolean isNumeric();

  double getNumericValue();
}
