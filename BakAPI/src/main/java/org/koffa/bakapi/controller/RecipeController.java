package org.koffa.bakapi.controller;

import org.koffa.bakapi.dto.Recipe;
import org.koffa.bakapi.kafka.RecipeKafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final RecipeKafkaProducer recipeKafkaProducer;
    public RecipeController(RecipeKafkaProducer recipeKafkaProducer) {
        this.recipeKafkaProducer = recipeKafkaProducer;
    }
    @PostMapping("/publish")
    public String publish(@RequestBody Recipe recipe) {
        try {
            recipeKafkaProducer.sendMessage(recipe);
            return "Recipe published successfully " + recipe;
        }
        catch(Exception e) {
            return "Recipe publishing failed " + e.getMessage();
        }
    }
}