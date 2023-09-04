package org.koffa.bakconsumerdatabase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bakconsumerdatabase.repository.IngredientRepository;
import org.koffa.bakconsumerdatabase.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipeKafkaDatabaseConsumer.class})
@ExtendWith(SpringExtension.class)
class RecipeKafkaDatabaseConsumerTest {
    @MockBean
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeKafkaDatabaseConsumer recipeKafkaDatabaseConsumer;

    @MockBean
    private RecipeRepository recipeRepository;

    /**
     * Method under test: {@link RecipeKafkaDatabaseConsumer#consume(String)}
     */
    @Test
    void testConsume() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        recipeKafkaDatabaseConsumer.consume("Not all who wander are lost");
    }

    /**
     * Method under test: {@link RecipeKafkaDatabaseConsumer#consume(String)}
     */
    @Test
    void testConsume2() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        recipeKafkaDatabaseConsumer.consume("Message");
    }

    /**
     * Method under test: {@link RecipeKafkaDatabaseConsumer#consume(String)}
     */
    @Test
    void testConsume3() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        recipeKafkaDatabaseConsumer.consume("42");
    }

    /**
     * Method under test: {@link RecipeKafkaDatabaseConsumer#consume(String)}
     */
    @Test
    void testConsume4() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        recipeKafkaDatabaseConsumer.consume("");
    }
}

