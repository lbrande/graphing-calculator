module se.lovebrandefelt.graphingcalculator.gui {
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires se.lovebrandefelt.graphingcalculator;

  exports se.lovebrandefelt.graphingcalculator.gui;

  opens se.lovebrandefelt.graphingcalculator.gui to
      javafx.fxml;
}
