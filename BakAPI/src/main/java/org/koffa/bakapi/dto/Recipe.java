package org.koffa.bakapi.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@ToString
@Getter
@Setter
public class Recipe {
    private long id;
    private String name;
    private String description;
    private List<RecipeIngredient> recipeIngredients;
    private List<String> steps;
    public void addStep(String step) {
        if(this.steps == null) this.steps = new ArrayList<>();
        this.steps.add(step);
    }
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if(this.recipeIngredients == null) this.recipeIngredients = new ArrayList<>();
        this.recipeIngredients.add(recipeIngredient);
    }
    @Getter
    @Setter
    @ToString
    static class Ingredient {
        private String name;
    }
    @Getter
    @Setter
    @ToString
    public static class RecipeIngredient {
        private Ingredient ingredient;
        private int quantity;
        private String unit;
    }
}