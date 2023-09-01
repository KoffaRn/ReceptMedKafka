package org.koffa.javafxgui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter @Setter @ToString @NoArgsConstructor
public class Recipe {
    private String name;
    private String description;
    private List<RecipeIngredient> recipeIngredients;
    private List<String> steps;
}