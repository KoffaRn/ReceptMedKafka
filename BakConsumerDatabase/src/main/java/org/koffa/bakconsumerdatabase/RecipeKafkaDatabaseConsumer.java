package org.koffa.bakconsumerdatabase;

import com.google.gson.Gson;
import org.koffa.bakconsumerdatabase.dto.Recipe;
import org.koffa.bakconsumerdatabase.dto.RecipeIngredient;
import org.koffa.bakconsumerdatabase.repository.IngredientRepository;
import org.koffa.bakconsumerdatabase.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RecipeKafkaDatabaseConsumer {
    private final static Logger logger = LoggerFactory.getLogger(RecipeKafkaDatabaseConsumer.class);
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Creates a new RecipeKafkaDatabaseConsumer
     * @param recipeRepository the recipe repository
     * @param ingredientRepository the ingredient repository
     */
    public RecipeKafkaDatabaseConsumer(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Consumes a message from the kafka topic
     * @param message the message to consume
     */
    @KafkaListener(topics = "recipeTopic", groupId = "dbGroup")
    public void consume(String message) {
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(message, Recipe.class);
        if(recipe.getRecipeIngredients() != null) persistRecipeIngredients(recipe);
        logger.info(String.format("#### -> Consumed message -> %s", recipe));
        recipeRepository.save(recipe);
    }

    //Persists recipes ingredients to the database
    private void persistRecipeIngredients(Recipe recipe) {
        for(RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            //If the ingredient is null, skip it
            if(recipeIngredient.getIngredient() == null) continue;
            //If the ingredient already exists, skip it
            if(ingredientRepository.existsByName(recipeIngredient.getIngredient().getName())) {
                logger.info(String.format("#### -> Ingredient already exists -> %s", recipeIngredient.getIngredient().getName()));
            }
            //If the ingredient does not exist, save it to the database
            else {
                logger.info(String.format("#### -> Ingredient does not exist -> %s, saving to db", recipeIngredient.getIngredient().getName()));
                ingredientRepository.save(recipeIngredient.getIngredient());
            }
            //Set the recipe ingredient's ingredient to the one in the database
            recipeIngredient.setIngredient(ingredientRepository.findByName(recipeIngredient.getIngredient().getName()));
        }
    }
}