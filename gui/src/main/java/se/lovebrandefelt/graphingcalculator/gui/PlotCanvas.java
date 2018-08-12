package se.lovebrandefelt.graphingcalculator.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import se.lovebrandefelt.graphingcalculator.Function;

public class PlotCanvas extends Canvas {
  private static final Color FUNCTION_COLOR = new Color(0.75, 0.25, 0, 1);
  private static final Color GRID_COLOR = new Color(0.25, 0.25, 0.25, 1);
  private static final Color AXIS_COLOR = new Color(0.75, 0.75, 0.75, 1);
  private static final int AXIS_ARROW_SIZE = 10;

  private Function function;
  private FunctionType functionType = FunctionType.NORMAL;
  private double gridX;
  private double gridY;
  private double minX;
  private double maxX;
  private double minY;
  private double maxY;
  private double stepX;
  private double minT;
  private double maxT;
  private double stepT;

  private void repaint() {
    clear();
    paintAxes();
    if (function != null) {
      if (functionType == FunctionType.NORMAL) {
        paintNormalFunction();
      } else if (functionType == FunctionType.POLAR) {
        paintPolarFunction();
      }
    }
  }

  private void clear() {
    getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
  }

  private void paintNormalFunction() {
    getGraphicsContext2D().beginPath();
    getGraphicsContext2D().moveTo(toScreenX(minX), toScreenY(function.evaluate(minX)));
    for (var x = minX + stepX; x < maxX + stepX; x += stepX) {
      getGraphicsContext2D().lineTo(toScreenX(x), toScreenY(function.evaluate(x)));
    }
    getGraphicsContext2D().setStroke(FUNCTION_COLOR);
    getGraphicsContext2D().stroke();
  }

  private void paintPolarFunction() {
    getGraphicsContext2D().beginPath();
    var t = minT;
    var r = function.evaluate(t);
    getGraphicsContext2D().moveTo(toScreenX(Math.cos(t) * r), toScreenY(Math.sin(t) * r));
    for (t = minT + stepT; t < maxT + stepT; t += stepT) {
      r = function.evaluate(t);
      getGraphicsContext2D().lineTo(toScreenX(Math.cos(t) * r), toScreenY(Math.sin(t) * r));
    }
    getGraphicsContext2D().setStroke(FUNCTION_COLOR);
    getGraphicsContext2D().stroke();
  }

  private void paintAxes() {
    getGraphicsContext2D().setFill(AXIS_COLOR);

    getGraphicsContext2D().setStroke(GRID_COLOR);
    getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
    getGraphicsContext2D().setTextBaseline(VPos.CENTER);
    if (toScreenX(0) >= 0 && toScreenX(0) <= getWidth()) {
      for (var y = -gridY; y > minY; y -= gridY) {
        getGraphicsContext2D().strokeLine(0, toScreenY(y), getWidth(), toScreenY(y));
        getGraphicsContext2D()
            .fillText(String.valueOf(y), toScreenX(0) + AXIS_ARROW_SIZE / 2, toScreenY(y));
      }
      for (var y = gridY; y < maxY; y += gridY) {
        getGraphicsContext2D().strokeLine(0, toScreenY(y), getWidth(), toScreenY(y));
        getGraphicsContext2D()
            .fillText(String.valueOf(y), toScreenX(0) + AXIS_ARROW_SIZE / 2, toScreenY(y));
      }
      getGraphicsContext2D().setStroke(AXIS_COLOR);
      getGraphicsContext2D().strokeLine(toScreenX(0), getHeight(), toScreenX(0), toScreenY(maxY));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {
                toScreenX(0) - AXIS_ARROW_SIZE / 2, toScreenX(0), toScreenX(0) + AXIS_ARROW_SIZE / 2
              },
              new double[] {AXIS_ARROW_SIZE, 0, AXIS_ARROW_SIZE},
              3);
    } else {
      for (var y = -gridY; y > minY; y -= gridY) {
        getGraphicsContext2D().strokeLine(0, toScreenY(y), getWidth(), toScreenY(y));
        getGraphicsContext2D().fillText(String.valueOf(y), 0, toScreenY(y));
      }
      for (var y = gridY; y < maxY; y += gridY) {
        getGraphicsContext2D().strokeLine(0, toScreenY(y), getWidth(), toScreenY(y));
        getGraphicsContext2D().fillText(String.valueOf(y), 0, toScreenY(y));
      }
    }

    getGraphicsContext2D().setStroke(GRID_COLOR);
    getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
    getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
    if (toScreenY(0) >= 0 && toScreenY(0) <= getHeight()) {
      for (var x = -gridX; x > minX; x -= gridX) {
        getGraphicsContext2D().strokeLine(toScreenX(x), 0, toScreenX(x), getHeight());
        getGraphicsContext2D()
            .fillText(String.valueOf(x), toScreenX(x), toScreenY(0) - AXIS_ARROW_SIZE / 2);
      }
      for (var x = gridX; x < maxX; x += gridX) {
        getGraphicsContext2D().strokeLine(toScreenX(x), 0, toScreenX(x), getHeight());
        getGraphicsContext2D()
            .fillText(String.valueOf(x), toScreenX(x), toScreenY(0) - AXIS_ARROW_SIZE / 2);
      }
      getGraphicsContext2D().setStroke(AXIS_COLOR);
      getGraphicsContext2D().strokeLine(0, toScreenY(0), toScreenX(maxX), toScreenY(0));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {getWidth() - AXIS_ARROW_SIZE, getWidth(), getWidth() - AXIS_ARROW_SIZE},
              new double[] {
                toScreenY(0) - AXIS_ARROW_SIZE / 2, toScreenY(0), toScreenY(0) + AXIS_ARROW_SIZE / 2
              },
              3);
    } else {
      for (var x = -gridX; x > minX; x -= gridX) {
        getGraphicsContext2D().strokeLine(toScreenX(x), 0, toScreenX(x), getHeight());
        getGraphicsContext2D().fillText(String.valueOf(x), toScreenX(x), getHeight());
      }
      for (var x = gridX; x < maxX; x += gridX) {
        getGraphicsContext2D().strokeLine(toScreenX(x), 0, toScreenX(x), getHeight());
        getGraphicsContext2D().fillText(String.valueOf(x), toScreenX(x), getHeight());
      }
    }
  }

  private double toScreenX(double x) {
    return AXIS_ARROW_SIZE / 2 + (x - minX) / (maxX - minX) * (getWidth() - AXIS_ARROW_SIZE);
  }

  private double toScreenY(double y) {
    return AXIS_ARROW_SIZE / 2
        + (getHeight() - AXIS_ARROW_SIZE)
        - (y - minY) / (maxY - minY) * (getHeight() - AXIS_ARROW_SIZE);
  }

  void setFunction(Function function, FunctionType type) {
    this.function = function;
    functionType = type;
    repaint();
  }

  void removeFunction(FunctionType newType) {
    function = null;
    functionType = newType;
    repaint();
  }

  void setGrid(double gridX, double gridY) {
    this.gridX = gridX;
    this.gridY = gridY;
    repaint();
  }

  void setNormalView(double minX, double maxX, double minY, double maxY, double stepX) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.stepX = stepX;
    repaint();
  }

  void setPolarView(double minT, double maxT, double stepT) {
    this.minT = minT;
    this.maxT = maxT;
    this.stepT = stepT;
    repaint();
  }
}
