package org.koffa.javafxgui.recipegui.sendgui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class IngredientBox extends VBox {
    final SplitPane ingredients = new SplitPane();
    final SplitPane ingredientButtons = new SplitPane();
    public IngredientBox(int width) {
        this.setMinWidth(width - 3);
        this.setMaxWidth(width - 3);
        ingredients.setOrientation(javafx.geometry.Orientation.VERTICAL);
        addIngredientPane(ingredients);
        Button addIngredientButton = new Button("Lägg till ingrediens");
        Button removeIngredient = new Button("Ta bort ingrediens");
        ingredientButtons.getItems().addAll(addIngredientButton, removeIngredient);
        addIngredientButton.setOnAction(event -> addIngredientPane(ingredients));
        removeIngredient.setOnAction(event -> {
            if (ingredients.getItems().size() > 1) {
                ingredients.getItems().remove(ingredients.getItems().size() - 1);
            }
        });
        this.getChildren().addAll(ingredientButtons, ingredients);
    }

    private void addIngredientPane(SplitPane ingredients) {
        IngredientPane ingredientPane = new IngredientPane();
        ingredients.getItems().add(ingredientPane);
    }
    public ObservableList<Node> getIngredients() {
        return ingredients.getItems();
    }
}
