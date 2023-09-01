package org.koffa.bakconsumerdatabase.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    public List<RecipeIngredient> recipeIngredients;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> steps;

}