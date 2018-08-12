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
  private double minT;
  private double maxT;
  private double stepT;

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

  private void repaint() {
    clear();
    if (functionType == FunctionType.NORMAL) {
      paintNormalGraph();
    } else if (functionType == FunctionType.POLAR) {
      paintPolarGraph();
    }
  }

  private void clear() {
    getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
  }

  private void paintNormalGraph() {
    paintAxes();
    if (function != null) {
      paintNormalFunction();
    }
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

  private void paintPolarGraph() {
    if (function != null) {
      var xs = new double[(int) ((maxT - minT) / stepT + 1)];
      var minX = Double.MAX_VALUE;
      var maxX = Double.MIN_VALUE;
      var ys = new double[(int) ((maxT - minT) / stepT + 1)];
      var minY = Double.MAX_VALUE;
      var maxY = Double.MIN_VALUE;
      for (var i = 0; i <= (maxT - minT) / stepT; i++) {
        var t = minT + i * stepT;
        var r = function.evaluate(t);
        xs[i] = r * Math.cos(t);
        if (xs[i] < minX) {
          minX = xs[i];
        }
        if (xs[i] > maxX) {
          maxX = xs[i];
        }
        ys[i] = r * Math.sin(t);
        if (ys[i] < minY) {
          minY = ys[i];
        }
        if (ys[i] > maxY) {
          maxY = ys[i];
        }
      }

      paintAxes(minX, maxX, minY, maxY);
      paintPolarFunction(xs, minX, maxX, ys, minY, maxY);
    }
  }

  private void paintPolarFunction(
      double[] xs, double minX, double maxX, double[] ys, double minY, double maxY) {
    getGraphicsContext2D().beginPath();
    getGraphicsContext2D().moveTo(toScreenX(xs[0], minX, maxX), toScreenY(ys[0], minY, maxY));
    for (var i = 1; i < xs.length; i++) {
      getGraphicsContext2D().lineTo(toScreenX(xs[i], minX, maxX), toScreenY(ys[i], minY, maxY));
    }
    getGraphicsContext2D().setStroke(FUNCTION_COLOR);
    getGraphicsContext2D().stroke();
  }

  private void paintAxes() {
    paintAxes(minX, maxX, minY, maxY);
  }

  private void paintAxes(double minX, double maxX, double minY, double maxY) {
    getGraphicsContext2D().setStroke(AXIS_COLOR);
    getGraphicsContext2D().setFill(AXIS_COLOR);
    if (toScreenX(0, minX, maxX) < 0 || toScreenX(0, minX, maxX) > getWidth()) {
      getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      getGraphicsContext2D().fillText(String.valueOf(minY), getWidth() / 2, getHeight());
      getGraphicsContext2D().setTextBaseline(VPos.TOP);
      getGraphicsContext2D().fillText(String.valueOf(maxY), getWidth() / 2, 0);
    } else {
      getGraphicsContext2D()
          .strokeLine(
              toScreenX(0, minX, maxX),
              toScreenY(minY, minY, maxY),
              toScreenX(0, minX, maxX),
              toScreenY(maxY, minY, maxY));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {
                toScreenX(0, minX, maxX) - AXIS_ARROW_SIZE / 2,
                toScreenX(0, minX, maxX),
                toScreenX(0, minX, maxX) + AXIS_ARROW_SIZE / 2
              },
              new double[] {AXIS_ARROW_SIZE, 0, AXIS_ARROW_SIZE},
              3);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      if (minY != 0) {
        getGraphicsContext2D()
            .fillText(
                String.valueOf(minY), toScreenX(0, minX, maxX) + AXIS_ARROW_SIZE / 2, getHeight());
      }
      getGraphicsContext2D().setTextBaseline(VPos.TOP);
      if (maxY != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(maxY), toScreenX(0, minX, maxX) + AXIS_ARROW_SIZE / 2, 0);
      }
    }

    if (toScreenY(0, minY, maxY) < 0 || toScreenY(0, minY, maxY) > getHeight()) {
      getGraphicsContext2D().setTextBaseline(VPos.CENTER);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      getGraphicsContext2D().fillText(String.valueOf(minX), 0, getHeight() / 2);
      getGraphicsContext2D().setTextAlign(TextAlignment.RIGHT);
      getGraphicsContext2D().fillText(String.valueOf(maxX), getWidth(), getHeight() / 2);
    } else {
      getGraphicsContext2D()
          .strokeLine(
              toScreenX(minX, minX, maxX),
              toScreenY(0, minY, maxY),
              toScreenX(maxX, minX, maxX),
              toScreenY(0, minY, maxY));
      getGraphicsContext2D()
          .fillPolygon(
              new double[] {getWidth() - AXIS_ARROW_SIZE, getWidth(), getWidth() - AXIS_ARROW_SIZE},
              new double[] {
                toScreenY(0, minY, maxY) - AXIS_ARROW_SIZE / 2,
                toScreenY(0, minY, maxY),
                toScreenY(0, minY, maxY) + AXIS_ARROW_SIZE / 2
              },
              3);
      getGraphicsContext2D().setTextBaseline(VPos.BOTTOM);
      getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
      if (minX != 0) {
        getGraphicsContext2D()
            .fillText(String.valueOf(minX), 0, toScreenY(0, minY, maxY) - AXIS_ARROW_SIZE / 2);
      }
      getGraphicsContext2D().setTextAlign(TextAlignment.RIGHT);
      if (maxX != 0) {
        getGraphicsContext2D()
            .fillText(
                String.valueOf(maxX), getWidth(), toScreenY(0, minY, maxY) - AXIS_ARROW_SIZE / 2);
      }
    }
  }

  private double toScreenX(double x) {
    return toScreenX(x, minX, maxX);
  }

  private double toScreenX(double x, double minX, double maxX) {
    return AXIS_ARROW_SIZE / 2 + (x - minX) / (maxX - minX) * (getWidth() - AXIS_ARROW_SIZE);
  }

  private double toScreenY(double y) {
    return toScreenY(y, minY, maxY);
  }

  private double toScreenY(double y, double minY, double maxY) {
    return AXIS_ARROW_SIZE / 2
        + (getHeight() - AXIS_ARROW_SIZE)
        - (y - minY) / (maxY - minY) * (getHeight() - AXIS_ARROW_SIZE);
  }
}
