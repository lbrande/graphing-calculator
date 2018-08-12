package se.lovebrandefelt.graphingcalculator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import se.lovebrandefelt.graphingcalculator.Function;

public class GuiController {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";

  private static final String ILLEGAL_RANGE_ERROR_MESSAGE = "(%s:%s) is not a legal range";
  private static final String NOT_NUMERIC_ERROR_MESSAGE = "\"%s\" is non-numeric.";
  private static final String NOT_POSITIVE_ERROR_MESSAGE = "\"%s\" is non-positive.";

  @FXML private PlotCanvas plotCanvas;
  @FXML private TextField functionField;
  @FXML private RadioButton normalFunction;
  @FXML private RadioButton polarFunction;
  @FXML private TextField minX;
  @FXML private TextField maxX;
  @FXML private TextField minY;
  @FXML private TextField maxY;
  @FXML private TextField stepX;
  @FXML private TextField minT;
  @FXML private TextField maxT;
  @FXML private TextField stepT;

  @FXML
  private void initialize() {
    updateView();
  }

  @FXML
  private void onFunctionChange() {
    unmarkFields(functionField);

    if (functionField.getText().trim().isEmpty()) {
      plotCanvas.removeFunction();
    } else {
      try {
        if (normalFunction.isSelected()) {
          plotCanvas.setFunction(new Function(functionField.getText(), 'x'), FunctionType.NORMAL);
        } else if (polarFunction.isSelected()) {
          plotCanvas.setFunction(new Function(functionField.getText(), 't'), FunctionType.POLAR);
        }
      } catch (IllegalArgumentException e) {
        markField(functionField, e.getMessage());
        plotCanvas.removeFunction();
      }
    }
  }

  @FXML
  private void onViewChange() {
    unmarkFields(minX, maxX, minY, maxY, stepX);

    if (checkAndMarkRangeFields(minX, maxX)
        && checkAndMarkRangeFields(minY, maxY)
        && checkAndMarkPositiveField(stepX)) {
      updateView();
    }
  }

  private void updateView() {
    plotCanvas.setView(
        Double.valueOf(minX.getText()),
        Double.valueOf(maxX.getText()),
        Double.valueOf(minY.getText()),
        Double.valueOf(maxY.getText()),
        Double.valueOf(stepX.getText()));
  }

  private boolean checkAndMarkRangeFields(TextField min, TextField max) {
    if (min.getText().matches(NUMERIC_VALUE_REGEX) && max.getText().matches(NUMERIC_VALUE_REGEX)) {
      if (Double.valueOf(min.getText()) >= Double.valueOf(max.getText())) {
        markField(min, String.format(ILLEGAL_RANGE_ERROR_MESSAGE, min.getText(), max.getText()));
        markField(max, String.format(ILLEGAL_RANGE_ERROR_MESSAGE, min.getText(), max.getText()));
        return false;
      } else {
        return true;
      }
    } else if (!min.getText().matches(NUMERIC_VALUE_REGEX)) {
      markField(min, String.format(NOT_NUMERIC_ERROR_MESSAGE, min.getText()));
      if (!max.getText().matches(NUMERIC_VALUE_REGEX)) {
        markField(max, String.format(NOT_NUMERIC_ERROR_MESSAGE, max.getText()));
      }
      return false;
    } else if (!max.getText().matches(NUMERIC_VALUE_REGEX)) {
      markField(max, String.format(NOT_NUMERIC_ERROR_MESSAGE, max.getText()));
      return false;
    } else {
      return true;
    }
  }

  private boolean checkAndMarkPositiveField(TextField field) {
    if (field.getText().matches(NUMERIC_VALUE_REGEX)) {
      if (Double.valueOf(field.getText()) <= 0) {
        markField(field, String.format(NOT_POSITIVE_ERROR_MESSAGE, field.getText()));
        return false;
      } else {
        return true;
      }
    } else {
      markField(field, String.format(NOT_NUMERIC_ERROR_MESSAGE, field.getText()));
      return false;
    }
  }

  private void markField(TextField field, String error) {
    field.getStyleClass().add("error");
    Tooltip errorTooltip = new Tooltip(error);
    errorTooltip.setShowDelay(Duration.ZERO);
    errorTooltip.setShowDuration(Duration.INDEFINITE);
    errorTooltip.setHideDelay(Duration.ZERO);
    field.setTooltip(new Tooltip(error));
  }

  private void unmarkFields(TextField... fields) {
    for (TextField field : fields) {
      field.getStyleClass().remove("error");
      field.setTooltip(null);
    }
  }
}
