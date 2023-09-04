package org.koffa.bakapi.controller;

import static org.mockito.Mockito.doNothing;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bakapi.dto.Recipe;
import org.koffa.bakapi.kafka.RecipeKafkaProducer;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecipeController.class})
@ExtendWith(SpringExtension.class)
class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    @MockBean
    private RecipeKafkaProducer recipeKafkaProducer;

    /**
     * Method under test: {@link RecipeController#publish(Recipe)}
     */
    @Test
    void testPublish() throws Exception {
        doNothing().when(recipeKafkaProducer).sendMessage(Mockito.any());

        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setName("Name");
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(recipe);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/recipe/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "Message received and sent to kafka: Recipe(name=Name, description=The characteristics of someone or"
                                        + " something, recipeIngredients=[], steps=[])"));
    }
}

