package se.lovebrandefelt.graphingcalculator.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
  private static final String FXML_LOCATION = "/gui.fxml";
  private static final String CSS_LOCATION = "/gui.css";
  private static final String TITLE = "Graphing Calculator";

  @Override
  public void start(Stage primaryStage) throws IOException {
    var scene = new Scene(FXMLLoader.load(getClass().getResource(FXML_LOCATION)));
    scene.getStylesheets().add(getClass().getResource(CSS_LOCATION).toString());
    primaryStage.setScene(scene);
    primaryStage.setTitle(TITLE);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
