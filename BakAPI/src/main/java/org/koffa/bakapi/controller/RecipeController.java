package org.koffa.bakapi.controller;

import org.koffa.bakapi.dto.Recipe;
import org.koffa.bakapi.kafka.RecipeKafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final RecipeKafkaProducer recipeKafkaProducer;
    public RecipeController(RecipeKafkaProducer recipeKafkaProducer) {
        this.recipeKafkaProducer = recipeKafkaProducer;
    }
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Recipe recipe) {
        try {
            recipeKafkaProducer.sendMessage(recipe);
            return ResponseEntity.ok().body("Message received and sent to kafka: " + recipe.toString());
        }
        catch(Exception e) {
            return ResponseEntity.internalServerError().body("Error sending message to kafka: " + e.getMessage());
        }
    }
}