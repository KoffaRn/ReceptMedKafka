package org.koffa.javafxgui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.javafxgui.dto.Ingredient;
import org.koffa.javafxgui.dto.Recipe;
import org.koffa.javafxgui.dto.RecipeIngredient;
import org.koffa.javafxgui.kafka.KafkaClient;
import org.koffa.javafxgui.textformatter.PositiveIntegerFilter;
import org.koffa.javafxgui.textformatter.PositiveIntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;


public class GraphicalUI extends Application {
    KafkaClient kafkaClient;
    @Override
    public void start(Stage stage) {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();

        // Recipe pane
        Label recipeName = new Label("Namn på recept");
        TextField receipeNameTextField = new TextField();
        receipeNameTextField.setText("Skriv namn på receptet här");
        TextArea recipeDescription = new TextArea("Beskrivning av recept");
        vBox.getChildren().addAll(recipeName, receipeNameTextField, recipeDescription);

        //Recipe steps pane
        SplitPane steps = new SplitPane();
        steps.setOrientation(javafx.geometry.Orientation.VERTICAL);
        SplitPane stepButtons = new SplitPane();
        Button addStepButton = new Button("Lägg till steg");
        Button removeStep = new Button("Ta bort steg");
        stepButtons.getItems().addAll(addStepButton, removeStep);
        steps.getItems().addAll(new TextField("Skriv steg här"));

        //Ingredient pane
        SplitPane ingredients = new SplitPane();
        ingredients.setOrientation(javafx.geometry.Orientation.VERTICAL);
        SplitPane ingredientButtons = new SplitPane();
        Button addIngredientButton = new Button("Lägg till ingrediens");
        Button removeIngredient = new Button("Ta bort ingrediens");
        ingredientButtons.getItems().addAll(addIngredientButton, removeIngredient);
        addIngredientPane(ingredients);

        //Send & Kafka pane
        Button sendButton = new Button("Skicka");
        Label logLabel = new Label("Log window");
        TextArea logTextArea = new TextArea();
        logTextArea.setText("");
        logTextArea.setEditable(false);


        // Adding to window
        vBox.getChildren().addAll(
                stepButtons,
                steps,
                ingredientButtons,
                ingredients,
                sendButton,
                logLabel,
                logTextArea);
        scrollPane.setContent(vBox);
        Scene scene = new Scene(scrollPane, 520, 800);
        stage.setScene(scene);
        stage.show();

        // Start kafka consumer in own thread
        kafkaClient = new KafkaClient(logTextArea);
        kafkaClient.addTopic("recipeTopic");
        Thread kafkaThread = new Thread(kafkaClient);
        kafkaThread.start();

        // Add actions to buttons
        addIngredientButton.setOnAction(event -> addIngredientPane(ingredients));

        removeIngredient.setOnAction(event -> {
            if (ingredients.getItems().size() > 1) {
                ingredients.getItems().remove(ingredients.getItems().size() - 1);
            }
        });
        addStepButton.setOnAction(event -> steps.getItems().addAll(new TextField("Skriv steg här")));
        removeStep.setOnAction(event -> {
            if (steps.getItems().size() > 1) {
                steps.getItems().remove(steps.getItems().size() - 1);
            }
        });
        sendButton.setOnAction(event -> {
            Recipe recipe = getRecipe(receipeNameTextField, recipeDescription, steps, ingredients);
            sendRecipe(recipe, logTextArea);
        });
    }

    // Get recipe from GUI
    private static Recipe getRecipe(TextField receipeNameTextField, TextArea recipeDescription, SplitPane steps, SplitPane ingredients) {
        Recipe recipe = new Recipe();
        recipe.setName(receipeNameTextField.getText());
        recipe.setDescription(recipeDescription.getText());
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        for (Node node : steps.getItems()) {
            TextField textField = (TextField) node;
            recipe.getSteps().add(textField.getText());
        }
        for (Node node : ingredients.getItems()) {
            SplitPane ingredientPane = (SplitPane) node;
            Ingredient ingredient = new Ingredient();
            ingredient.setName(((TextField) ingredientPane.getItems().get(2)).getText());
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setQuantity(Integer.parseInt(((TextField) ingredientPane.getItems().get(0)).getText()));
            recipeIngredient.setUnit(((TextField) ingredientPane.getItems().get(1)).getText());
            recipe.getRecipeIngredients().add(recipeIngredient);
        }
        return recipe;
    }

    // Send recipe to API
    private void sendRecipe(Recipe recipe, TextArea logTextArea) {
        try {
            RecipeSender recipeSender = new RecipeSender();
            logTextArea.appendText("Recipe sent to API >> " + recipeSender.sendRecipe(recipe) + "\n");
        } catch (IOException e) {
            logTextArea.appendText("Recipe failed send to API > Response >>> " + e.getMessage()+ "\n");
        }
    }

    // Add ingredient pane
    private static void addIngredientPane(SplitPane ingredients) {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new PositiveIntegerStringConverter(), 0, new PositiveIntegerFilter());
        SplitPane ingredientsPane = new SplitPane();
        ingredientsPane.setDividerPositions(0.1, 0.2, 0.9);
        TextField amount = new TextField("123");
        amount.setTextFormatter(textFormatter);
        ingredientsPane.getItems().addAll(amount, new TextField("Enhet"), new TextField("Namn på ingrediens"));
        ingredients.getItems().addAll(ingredientsPane);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        kafkaClient.interrupt();
    }
}