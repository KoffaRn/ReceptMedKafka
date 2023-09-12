package org.koffa.javafxgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.koffa.javafxgui.helpers.ConfigManager;
import org.koffa.javafxgui.helpers.RecipeApiFacade;
import org.koffa.javafxgui.recipegui.sendgui.*;



public class MainGUI extends Application {
    private SendBox sendBox;
    private String API_URL;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ConfigManager configManager = ConfigManager.getInstance();
        int WIDTH = configManager.getAPP_WIDTH();
        API_URL = configManager.getAPI_URL();
        RecipeApiFacade recipeApiFacade = getRecipeApiFacade();
        // Set up tabs
        ScrollPane sendscrollPane = new ScrollPane();
        TabPane tabPane = new TabPane();
        Tab sendTab = new Tab("Skicka recept");
        sendTab.setClosable(false);
        Tab getTab = new Tab("Hämta recept");
        getTab.setClosable(false);
        // Set up send tab
        sendBox = new SendBox(recipeApiFacade, configManager);
        sendscrollPane.setContent(sendBox);
        sendTab.setContent(sendscrollPane);
        // Set up get tab

        tabPane.getTabs().addAll(sendTab, getTab);
        Scene scene = new Scene(tabPane, WIDTH, 768);
        stage.setScene(scene);
        stage.show();
    }

    private RecipeApiFacade getRecipeApiFacade() {
        try {
            RecipeApiFacade recipeApiFacade = new RecipeApiFacade(API_URL);
            recipeApiFacade.test();
            return recipeApiFacade;
        } catch (Exception e) {
            System.err.println("Kunde inte ansluta till API >> " + e.getMessage());
            return null;
        }
    }
    //close the kafka client
    @Override
    public void stop() {
        sendBox.exit();
        Platform.exit();
    }
}
