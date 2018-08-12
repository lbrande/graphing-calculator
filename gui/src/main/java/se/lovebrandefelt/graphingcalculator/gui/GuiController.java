package se.lovebrandefelt.graphingcalculator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import se.lovebrandefelt.graphingcalculator.Function;

public class GuiController {
  private static final String NUMERIC_VALUE_REGEX = "-?\\d+(?:\\.\\d+)?";

  private static final String NO_FUNCTION_TYPE_ERROR_MESSAGE = "No function type is selected.";
  private static final String ILLEGAL_RANGE_ERROR_MESSAGE = "(%s:%s) is not a legal range.";
  private static final String NOT_NUMERIC_ERROR_MESSAGE = "\"%s\" is non-numeric.";
  private static final String NOT_POSITIVE_ERROR_MESSAGE = "\"%s\" is non-positive.";

  @FXML private PlotCanvas plotCanvas;
  @FXML private TextField functionField;
  @FXML private RadioButton normalFunction;
  @FXML private RadioButton polarFunction;
  @FXML private GridPane normalView;
  @FXML private TextField minX;
  @FXML private TextField maxX;
  @FXML private TextField minY;
  @FXML private TextField maxY;
  @FXML private TextField stepX;
  @FXML private GridPane polarView;
  @FXML private TextField minT;
  @FXML private TextField maxT;
  @FXML private TextField stepT;

  @FXML
  private void initialize() {
    updateNormalView();
    updatePolarView();
  }

  @FXML
  private void onFunctionChange() {
    unmarkFields(functionField);

    try {
      if (getFunctionType() == FunctionType.NORMAL) {
        normalView.setDisable(false);
        polarView.setDisable(true);
      } else if (getFunctionType() == FunctionType.POLAR) {
        normalView.setDisable(true);
        polarView.setDisable(false);
      }
      setFunction();
    } catch (IllegalArgumentException e) {
      markField(functionField, e.getMessage());
      plotCanvas.removeFunction(getFunctionType());
    }
  }

  @FXML
  private void onViewChange() {
    unmarkFields(minX, maxX, minY, maxY, stepX, minT, maxT, stepT);

    if (checkAndMarkRangeFields(minX, maxX)
        && checkAndMarkRangeFields(minY, maxY)
        && checkAndMarkPositiveField(stepX)) {
      updateNormalView();
    }

    if (checkAndMarkRangeFields(minT, maxT) && checkAndMarkPositiveField(stepT)) {
      updatePolarView();
    }
  }

  private void setFunction() {
    if (functionField.getText().trim().isEmpty()) {
      plotCanvas.removeFunction(getFunctionType());
    } else {
      try {
        plotCanvas.setFunction(
            new Function(
                functionField.getText(), getFunctionType() == FunctionType.NORMAL ? 'x' : 't'),
            getFunctionType());
      } catch (IllegalArgumentException e) {
        markField(functionField, e.getMessage());
        plotCanvas.removeFunction(getFunctionType());
      }
    }
  }

  private void updateNormalView() {
    plotCanvas.setNormalView(
        Double.valueOf(minX.getText()),
        Double.valueOf(maxX.getText()),
        Double.valueOf(minY.getText()),
        Double.valueOf(maxY.getText()),
        Double.valueOf(stepX.getText()));
  }

  private void updatePolarView() {
    plotCanvas.setPolarView(
        Double.valueOf(minT.getText()),
        Double.valueOf(maxT.getText()),
        Double.valueOf(stepT.getText()));
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

  private FunctionType getFunctionType() {
    if (normalFunction.isSelected()) {
      return FunctionType.NORMAL;
    } else if (polarFunction.isSelected()) {
      return FunctionType.POLAR;
    }
    throw new RuntimeException(NO_FUNCTION_TYPE_ERROR_MESSAGE);
  }
}
