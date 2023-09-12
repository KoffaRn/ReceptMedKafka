package org.koffa.javafxgui.recipegui.sendgui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.koffa.javafxgui.dto.Recipe;
import org.koffa.javafxgui.dto.RecipeIngredient;
import org.koffa.javafxgui.helpers.ConfigManager;
import org.koffa.javafxgui.helpers.RecipeSender;
import org.koffa.javafxgui.kafka.KafkaClient;

import java.util.ArrayList;

public class SendBox extends VBox {
    private final LoggerBox loggerBox;
    private final RecipeBox recipeBox;
    private final IngredientBox ingredientBox;
    private final KafkaClient kafkaClient;

    public SendBox(RecipeSender recipeSender, ConfigManager configManager) {
        int WIDTH = configManager.getAPP_WIDTH();
        this.loggerBox = new LoggerBox(WIDTH);
        this.recipeBox = new RecipeBox(WIDTH);
        this.ingredientBox = new IngredientBox(WIDTH);
        this.kafkaClient = new KafkaClient(
                configManager.getKAFKA_SERVER(),
                configManager.getKAFKA_TOPIC(),
                configManager.getKAFKA_GROUP_ID(),
                loggerBox
        );
        this.setMinWidth(WIDTH - 3);
        this.setMaxWidth(WIDTH - 3);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinWidth(WIDTH - 3);
        scrollPane.setMaxWidth(WIDTH - 3);
        scrollPane.setMinHeight(500);
        scrollPane.setMaxHeight(500);
        VBox vBox = new VBox();
        Button sendButton = new Button("Skicka");
        vBox.getChildren().addAll(
                recipeBox,
                ingredientBox,
                sendButton,
                loggerBox
        );
        scrollPane.setContent(vBox);
        this.getChildren().add(scrollPane);
        if(recipeSender == null) loggerBox.error("Kunde inte skapa RecipeSender, se till så att api.url är korrekt och starta om programmet.", new RuntimeException("API ERROR"));
        Thread kafkaThread = new Thread(kafkaClient);
        kafkaThread.start();
        sendButton.setOnAction(event -> {
            try {
                if (recipeSender != null)
                    loggerBox.info(recipeSender.send(getRecipe()));
            } catch (Exception e) {
                loggerBox.error("Kunde inte skicka recept", e);
            }
        });
    }

    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(recipeBox.getRecipeName());
        recipe.setDescription(recipeBox.getRecipeDescription());
        recipe.setSteps(recipeBox.getSteps());
        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
        for(Node node : ingredientBox.getIngredients())
            ingredients.add(((IngredientPane) node).getIngredient());
        recipe.setRecipeIngredients(ingredients);
        return recipe;
    }
    public void exit() {
        System.out.println("Closing kafka client");
        kafkaClient.interrupt();
    }
}
