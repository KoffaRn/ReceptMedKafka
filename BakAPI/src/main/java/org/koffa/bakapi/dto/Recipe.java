package org.koffa.bakapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class Recipe {
    private String name;
    private String description;
    private List<RecipeIngredient> recipeIngredients;
    private List<String> steps;
    @Getter
    @Setter
    @ToString
    static class Ingredient {
        private String name;
    }
    @Getter
    @Setter
    @ToString
    static class RecipeIngredient {
        private Ingredient ingredient;
        private int quantity;
        private String unit;
    }
}