package org.koffa.javafxgui.recipegui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.koffa.javafxgui.dto.Ingredient;
import org.koffa.javafxgui.dto.RecipeIngredient;
import org.koffa.javafxgui.textformatter.PositiveIntegerFilter;
import org.koffa.javafxgui.textformatter.PositiveIntegerStringConverter;

public class IngredientPane extends SplitPane {
    public IngredientPane() {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new PositiveIntegerStringConverter(),0, new PositiveIntegerFilter());
        this.setDividerPositions(0.1,0.2,0.9);
        TextField amount = new TextField("123");
        amount.setTextFormatter(textFormatter);
        this.getItems().addAll(amount, new TextField("Enhet"), new TextField("Namn på ingrediens"));
    }

    public RecipeIngredient getIngredient() {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setQuantity(Integer.parseInt(((TextField) this.getItems().get(0)).getText()));
        recipeIngredient.setUnit(((TextField) this.getItems().get(1)).getText());
        recipeIngredient.setIngredient(new Ingredient(((TextField) this.getItems().get(2)).getText()));
        return recipeIngredient;
    }
}
