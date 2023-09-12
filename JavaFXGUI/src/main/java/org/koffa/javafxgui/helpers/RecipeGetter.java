package org.koffa.javafxgui.helpers;

import org.koffa.javafxgui.dto.Recipe;

import java.util.List;

public interface RecipeGetter {
    Recipe getRecipe(long id);
    List<Recipe> getRecipes();
}