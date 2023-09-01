package org.koffa.javafxgui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecipeIngredient {
    private Ingredient ingredient;
    private int quantity;
    private String unit;
}