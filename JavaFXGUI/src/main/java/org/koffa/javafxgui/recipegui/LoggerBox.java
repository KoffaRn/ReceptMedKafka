package org.koffa.javafxgui.recipegui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class LoggerBox extends VBox{
    TextFlow logger = new TextFlow();
    public LoggerBox() {
        Label loggerLabel = new Label("Logger");
        this.getChildren().addAll(loggerLabel, logger);
    }
    private void log(Text text) {
        // Make sure the log is updated on the JavaFX thread
        Platform.runLater(() -> logger.getChildren().add(text));
    }

    public void info(String s) {
        Text text = new Text(s + "\n");
        text.setFill(Color.BLACK);
        log(text);
    }

    public void error(String s, Exception e) {
        Text text = new Text(s + " >> " + e.getMessage() + "\n");
        text.setFill(Color.RED);
        log(text);
    }
}