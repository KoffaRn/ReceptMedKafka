package org.koffa.gui.helpers;

import java.io.IOException;

public interface RecipeSender {
    String send(Recipe recipe) throws RuntimeException, IOException;
}

