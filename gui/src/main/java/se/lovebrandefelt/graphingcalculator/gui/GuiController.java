package se.lovebrandefelt.graphingcalculator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import se.lovebrandefelt.graphingcalculator.Function;

public class GuiController {
  @FXML private PlotCanvas plotCanvas;
  @FXML private TextField functionField;
  @FXML private TextField minX;
  @FXML private TextField maxX;
  @FXML private TextField minY;
  @FXML private TextField maxY;
  @FXML private TextField stepX;

  @FXML
  private void initialize() {
    updateView();
  }

  @FXML
  private void onFunctionTextFieldChange() {
    functionField.getStyleClass().remove("error");
    if (functionField.getText().isEmpty()) {
      plotCanvas.removeFunction();
    } else {
      updateFunction();
    }
  }

  @FXML
  private void onViewChange(KeyEvent keyEvent) {
    var textField = ((TextField) keyEvent.getSource());
    textField.getStyleClass().remove("error");
    try {
      Double.parseDouble(textField.getText());
    } catch (NumberFormatException e) {
      textField.getStyleClass().add("error");
    }
    updateView();
  }

  private void updateFunction() {
    try {
      plotCanvas.setFunction(new Function(functionField.getText(), 'x'));
    } catch (IllegalArgumentException e) {
      functionField.getStyleClass().add("error");
      plotCanvas.removeFunction();
    }
  }

  private void updateView() {
    plotCanvas.setView(
        Double.parseDouble(minX.getText()),
        Double.parseDouble(maxX.getText()),
        Double.parseDouble(minY.getText()),
        Double.parseDouble(maxY.getText()),
        Double.parseDouble(stepX.getText()));
  }
}
