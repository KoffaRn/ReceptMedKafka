package org.koffa.javafxgui.helpers;

import org.koffa.javafxgui.dto.Recipe;

import java.io.IOException;

public interface RecipeSender {
    String send(Recipe recipe) throws RuntimeException, IOException;
}
