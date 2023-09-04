package org.koffa.bakconsumerdatabase;

import com.google.gson.Gson;
import lombok.Getter;
import org.koffa.bakconsumerdatabase.dto.Recipe;
import org.koffa.bakconsumerdatabase.dto.RecipeIngredient;
import org.koffa.bakconsumerdatabase.repository.IngredientRepository;
import org.koffa.bakconsumerdatabase.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Getter
public class RecipeKafkaDatabaseConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(RecipeKafkaDatabaseConsumer.class);
    private final static String TOPIC = "recipeTopic";
    private final static String GROUP_ID = "dbGroup";

    final RecipeRepository recipeRepository;
    final IngredientRepository ingredientRepository;

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
     * Consumes a message in the form of a jsonString representing a recipe from the kafka topic
     * @param message the message (recipe) to consume
     */
    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(String message) {
        try {
            consumeRecipe(message);
            LOGGER.info(String.format("#### -> Consumed message/recipe -> %s", message));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void consumeRecipe(String message) {
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(message, Recipe.class);
        if(recipe.getRecipeIngredients() != null)
            persistRecipeIngredients(recipe);
        recipeRepository.save(recipe);
    }

    //Persists recipes ingredients to the database
    private void persistRecipeIngredients(Recipe recipe) {
        for(RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            //If the ingredient is null, skip it
            if(recipeIngredient.getIngredient() == null) continue;
            //If the ingredient already exists, skip it
            if(ingredientRepository.existsByName(recipeIngredient.getIngredient().getName())) {
                LOGGER.info(String.format("#### -> Ingredient already exists -> %s", recipeIngredient.getIngredient().getName()));
            }
            //If the ingredient does not exist, save it to the database
            else {
                LOGGER.info(String.format("#### -> Ingredient does not exist -> %s, saving to db", recipeIngredient.getIngredient().getName()));
                ingredientRepository.save(recipeIngredient.getIngredient());
            }
            //Set the recipe ingredient's ingredient to the one in the database
            recipeIngredient.setIngredient(ingredientRepository.findByName(recipeIngredient.getIngredient().getName()));
        }
    }
}