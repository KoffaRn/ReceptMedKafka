package org.koffa.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        stage.setTitle("Koffa GUI");
        stage.show();
    }
}