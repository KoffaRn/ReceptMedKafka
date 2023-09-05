package org.koffa.javafxgui.recipegui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class LoggerBox extends VBox{
    TextFlow logger = new TextFlow();
    public LoggerBox() {
        Label loggerLabel = new Label("Logger");
        this.getChildren().addAll(loggerLabel, logger);
    }
    private void log(Text text) {
        logger.getChildren().add(text);
    }

    public void info(String s) {
        Text text = new Text(s + "\n");
        text.setFill(javafx.scene.paint.Color.BLACK);
        log(text);
    }

    public void error(String s, Exception e) {
        Text text = new Text(s + " >> " + e.getMessage() + "\n");
        text.setFill(javafx.scene.paint.Color.RED);
        log(text);
    }
}