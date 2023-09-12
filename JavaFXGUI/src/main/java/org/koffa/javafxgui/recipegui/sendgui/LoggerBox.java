package org.koffa.javafxgui.recipegui.sendgui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class LoggerBox extends VBox {
    private final TextFlow logger = new TextFlow();
    public LoggerBox(int width) {
        Label loggerLabel = new Label("Logger");
        logger.setMaxWidth(width - 3);
        logger.setMinWidth(width - 3);
        this.getChildren().addAll(loggerLabel, logger);
    }
    private void log(Text text) {
        Text txtLine = new Text("--------------------\n");
        // Make sure the log is updated on the JavaFX thread
        Platform.runLater(() -> logger.getChildren().addAll(text,txtLine));
    }

    public void info(String s) {
        Text text = new Text(s + "\n");
        text.setFill(Color.GREEN);
        log(text);
    }
    public void error(String s, Throwable throwable) {
        Text text = new Text(s + " >> " + throwable.getMessage() + "\n");
        text.setFill(Color.RED);
        log(text);
    }
}