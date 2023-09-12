package org.koffa.javafxgui.helpers;

import org.koffa.javafxgui.dto.Recipe;

import java.util.ArrayList;
import java.util.List;

public interface RecipeGetter {
    public Recipe getRecipe(long id);
    public List<Recipe> getRecipes();
}
