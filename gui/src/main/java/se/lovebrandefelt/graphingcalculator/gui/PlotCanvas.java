package se.lovebrandefelt.graphingcalculator.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import se.lovebrandefelt.graphingcalculator.Function;

public class PlotCanvas extends Canvas {
  private static final Color AXIS_COLOR = new Color(0.75, 0.75, 0.75, 1);
  private static final Color FUNCTION_COLOR = new Color(0.75, 0.25, 0, 1);
  private static final int AXIS_ARROW_SIZE = 10;

  private Function function;
  private FunctionType functionType = FunctionType.NORMAL;
  private double minX;
  private double maxX;
  private double minY;
  private double maxY;
  private double stepX;

  void setFunction(Function function, FunctionType type) {
    this.function = function;
    functionType = type;
    repaint();
  }

  void removeFunction() {
    function = null;
    functionType = FunctionType.NORMAL;
    repaint();
  }

  void setView(double minX, double maxX, double minY, double maxY, double stepX) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.stepX = stepX;
    repaint();
  }

  private void repaint() {
    clear();
    if (functionType == FunctionType.NORMAL) {
      paintNormalAxes();
      if (function != null) {
        paintNormalFunction();
      }
    } else if (functionType == FunctionType.POLAR) {
      paintPolarAxes();
      if (function != null) {
        paintPolarFunction();
      }
    }
  }

  private void clear() {
    getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
  }

  private void paintNormalAxes() {
    getGraphicsContext2D().setStroke(AXIS_COLOR);
    getGraphicsContext2D().setFill(AXIS_COLOR);
    if (toScreenX(0) < 0 || toScreenX(0) > getWidth()) {
      getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      getGraphicsContext2D().fillText(String.valueOf(minY), getWidth() / 2, getHeight());
      getGraphicsContext2D().setTextBaseline(VPos.TOP);
      getGraphicsContext2D().fillText(String.valueOf(maxY), getWidth() / 2, 0);
    } else {
      getGraphicsContext2D()
          .strokeLine(toScreenX(0), toScreenY(minY), toScreenX(0), toScreenY(maxY));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {
                toScreenX(0) - AXIS_ARROW_SIZE / 2, toScreenX(0), toScreenX(0) + AXIS_ARROW_SIZE / 2
              },
              new double[] {AXIS_ARROW_SIZE, 0, AXIS_ARROW_SIZE},
              3);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      if (minY != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(minY), toScreenX(0) + AXIS_ARROW_SIZE / 2, getHeight());
      }
      getGraphicsContext2D().setTextBaseline(VPos.TOP);
      if (maxY != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(maxY), toScreenX(0) + AXIS_ARROW_SIZE / 2, 0);
      }
    }

    if (toScreenY(0) < 0 || toScreenY(0) > getHeight()) {
      getGraphicsContext2D().setTextBaseline(VPos.CENTER);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      getGraphicsContext2D().fillText(String.valueOf(minX), 0, getHeight() / 2);
      getGraphicsContext2D().setTextAlign(TextAlignment.RIGHT);
      getGraphicsContext2D().fillText(String.valueOf(maxX), getWidth(), getHeight() / 2);
    } else {
      getGraphicsContext2D()
          .strokeLine(toScreenX(minX), toScreenY(0), toScreenX(maxX), toScreenY(0));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {getWidth() - AXIS_ARROW_SIZE, getWidth(), getWidth() - AXIS_ARROW_SIZE},
              new double[] {
                toScreenY(0) - AXIS_ARROW_SIZE / 2, toScreenY(0), toScreenY(0) + AXIS_ARROW_SIZE / 2
              },
              3);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      if (minX != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(minX), 0, toScreenY(0) - AXIS_ARROW_SIZE / 2);
      }
      getGraphicsContext2D().setTextAlign(TextAlignment.RIGHT);
      if (maxX != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(maxX), getWidth(), toScreenY(0) - AXIS_ARROW_SIZE / 2);
      }
    }
  }

  private void paintNormalFunction() {
    getGraphicsContext2D().beginPath();
    getGraphicsContext2D().moveTo(toScreenX(minX), toScreenY(function.evaluate(minX)));
    for (var x = minX + stepX; x <= maxX + stepX; x += stepX) {
      getGraphicsContext2D().lineTo(toScreenX(x), toScreenY(function.evaluate(x)));
    }
    getGraphicsContext2D().setStroke(FUNCTION_COLOR);
    getGraphicsContext2D().stroke();
  }

  private void paintPolarFunction() {}

  private void paintPolarAxes() {}

  private double toScreenX(double x) {
    return AXIS_ARROW_SIZE / 2 + (x - minX) / (maxX - minX) * (getWidth() - AXIS_ARROW_SIZE);
  }

  private double toScreenY(double y) {
    return AXIS_ARROW_SIZE / 2
        + (getHeight() - AXIS_ARROW_SIZE)
        - (y - minY) / (maxY - minY) * (getHeight() - AXIS_ARROW_SIZE);
  }
}
