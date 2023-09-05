package org.koffa.javafxgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.javafxgui.dto.Recipe;
import org.koffa.javafxgui.dto.RecipeIngredient;
import org.koffa.javafxgui.helpers.RecipeSender;
import org.koffa.javafxgui.kafka.KafkaClient;
import org.koffa.javafxgui.recipegui.*;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.getProperty;

public class MainGUI extends Application {
    LoggerBox loggerBox = new LoggerBox();
    RecipeBox recipeBox = new RecipeBox();
    IngredientBox ingredientBox = new IngredientBox();
    KafkaClient kafkaClient;
    private static final String TOPIC = getProperty("topic", "recipeTopic");
public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Set up stage
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        stage.setTitle("ReceptGUI");
        Button sendButton = new Button("Skicka recept");
        loggerBox = new LoggerBox();
        kafkaClient = new KafkaClient(loggerBox);
        kafkaClient.addTopic(TOPIC);
        // Start the kafka client in a new thread
        Thread kafkaThread = new Thread(kafkaClient);
        kafkaThread.start();
        // Add the components to the stage and show it
        vBox.getChildren().addAll(
                recipeBox,
                ingredientBox,
                sendButton,
                loggerBox
        );
        scrollPane.setContent(vBox);
        Scene scene = new Scene(scrollPane, 550, 800);
        stage.setScene(scene);
        stage.show();

        // Add functionality to the send button
        sendButton.setOnAction(this::handle);
    }

    // Send the recipe to the API
    private String sendRecipe(Recipe recipe) throws IOException {
        RecipeSender recipeSender = new RecipeSender();
        return recipeSender.sendRecipe(recipe);

    }

    // Get the recipe from the GUI
    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(recipeBox.getRecipeName());
        recipe.setDescription(recipeBox.getRecipeDescription());
        recipe.setSteps(recipeBox.getSteps());
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (Node node : ingredientBox.getIngredients()) {
            recipeIngredients.add(((IngredientPane) node).getIngredient());
        }
        recipe.setRecipeIngredients(recipeIngredients);
        return recipe;
    }

    //close the kafka client
    @Override
    public void stop() {
        kafkaClient.interrupt();
        Platform.exit();
    }

    private void handle(ActionEvent event) {
        try {
            String response = sendRecipe(getRecipe());
            loggerBox.info("Send recipe to api >> " + response);
        } catch (Exception e) {
            loggerBox.error("Kunde inte skicka receptet till API >> ", e);
        }

    }
}
