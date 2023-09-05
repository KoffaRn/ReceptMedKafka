package org.koffa.javafxgui.recipegui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RecipeBox extends VBox {
    private final TextField receipeNameTextField = new TextField("Skriv namn på receptet här");
    private final TextArea recipeDescriptionTextArea = new TextArea("Beskrivning av recept");
    private final RecipeStepsBox recipeStepsBox = new RecipeStepsBox();
    public RecipeBox(int width) {
        this.setMinWidth(width - 3);
        this.setMaxWidth(width - 3);
        Label recipeDescription = new Label("Beskrivning av recept");
        Label recipeName = new Label("Namn på recept");
        this.getChildren().addAll(recipeName, receipeNameTextField, recipeDescription, recipeDescriptionTextArea, recipeStepsBox);
    }
    public ArrayList<String> getSteps() {
        ArrayList<String> steps = new ArrayList<>();
        for (Node node : recipeStepsBox.getSteps()) {
            steps.add(((TextField) node).getText());
        }
        return steps;
    }
    public String getRecipeName() {
        return receipeNameTextField.getText();
    }
    public String getRecipeDescription() {
        return recipeDescriptionTextArea.getText();
    }
}
