package se.lovebrandefelt.graphingcalculator.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import se.lovebrandefelt.graphingcalculator.Function;

public class PlotCanvas extends Canvas {
  private static final Color AXIS_COLOR = new Color(0.75, 0.75, 0.75, 1);
  private static final Color FUNCTION_COLOR = new Color(0.75, 0.75, 0, 1);
  private static final int AXIS_ARROW_SIZE = 10;

  private Function function;
  private double minX;
  private double maxX;
  private double minY;
  private double maxY;
  private double stepX;

  void setFunction(Function function) {
    this.function = function;
    repaint();
  }

  void removeFunction() {
    function = null;
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
    paintAxes();
    if (function != null) {
      paintFunction();
    }
  }

  private void clear() {
    getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
  }

  private void paintAxes() {
    getGraphicsContext2D().setStroke(AXIS_COLOR);
    getGraphicsContext2D().setFill(AXIS_COLOR);
    getGraphicsContext2D().setTextBaseline(VPos.CENTER);
    getGraphicsContext2D().strokeLine(toScreenX(0), toScreenY(minY), toScreenX(0), toScreenY(maxY));
    getGraphicsContext2D()
        .fillPolygon(
            new double[] {
              toScreenX(0) - AXIS_ARROW_SIZE / 2, toScreenX(0), toScreenX(0) + AXIS_ARROW_SIZE / 2
            },
            new double[] {AXIS_ARROW_SIZE, 0, AXIS_ARROW_SIZE},
            3);
    getGraphicsContext2D()
        .fillText(
            String.valueOf(minY),
            toScreenX(0) + AXIS_ARROW_SIZE,
            toScreenY(minY) - AXIS_ARROW_SIZE / 2);
    getGraphicsContext2D()
        .fillText(
            String.valueOf(maxY),
            toScreenX(0) + AXIS_ARROW_SIZE,
            toScreenY(maxY) + AXIS_ARROW_SIZE / 2);
    getGraphicsContext2D().strokeLine(toScreenX(minX), toScreenY(0), toScreenX(maxX), toScreenY(0));
    getGraphicsContext2D()
        .fillPolygon(
            new double[] {getWidth() - AXIS_ARROW_SIZE, getWidth(), getWidth() - AXIS_ARROW_SIZE},
            new double[] {
              toScreenY(0) - AXIS_ARROW_SIZE / 2, toScreenY(0), toScreenY(0) + AXIS_ARROW_SIZE / 2
            },
            3);
  }

  private void paintFunction() {
    getGraphicsContext2D().beginPath();
    getGraphicsContext2D().moveTo(toScreenX(minX), toScreenY(function.evaluate(minX)));
    for (var x = minX + stepX; x <= maxX + stepX; x += stepX) {
      getGraphicsContext2D().lineTo(toScreenX(x), toScreenY(function.evaluate(x)));
    }
    getGraphicsContext2D().setStroke(FUNCTION_COLOR);
    getGraphicsContext2D().stroke();
  }

  private double toScreenX(double x) {
    return AXIS_ARROW_SIZE / 2 + (x - minX) / (maxX - minX) * (getWidth() - AXIS_ARROW_SIZE);
  }

  private double toScreenY(double y) {
    return AXIS_ARROW_SIZE / 2
        + (getHeight() - AXIS_ARROW_SIZE)
        - (y - minY) / (maxY - minY) * (getHeight() - AXIS_ARROW_SIZE);
  }
}
