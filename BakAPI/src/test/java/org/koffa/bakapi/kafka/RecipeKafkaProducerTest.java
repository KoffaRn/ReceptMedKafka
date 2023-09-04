package org.koffa.bakapi.kafka;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bakapi.dto.Recipe;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipeKafkaProducer.class})
@ExtendWith(SpringExtension.class)
class RecipeKafkaProducerTest {
    @MockBean
    private KafkaTemplate<NewTopic, Recipe> kafkaTemplate;

    @Autowired
    private RecipeKafkaProducer recipeKafkaProducer;

    /**
     * Method under test: {@link RecipeKafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage() {
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(new CompletableFuture<>());

        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setName("Name");
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        recipeKafkaProducer.sendMessage(recipe);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
    }

    /**
     * Method under test: {@link RecipeKafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage2() {
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(new CompletableFuture<>());
        Recipe recipe = mock(Recipe.class);
        doNothing().when(recipe).setDescription(Mockito.any());
        doNothing().when(recipe).setName(Mockito.any());
        doNothing().when(recipe).setRecipeIngredients(Mockito.any());
        doNothing().when(recipe).setSteps(Mockito.any());
        recipe.setDescription("The characteristics of someone or something");
        recipe.setName("Name");
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        recipeKafkaProducer.sendMessage(recipe);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        verify(recipe).setDescription(Mockito.any());
        verify(recipe).setName(Mockito.any());
        verify(recipe).setRecipeIngredients(Mockito.any());
        verify(recipe).setSteps(Mockito.any());
    }

    /**
     * Method under test: {@link RecipeKafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage3() {
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<Recipe>any())).thenReturn(new CompletableFuture<>());

        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setName("Name");
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        recipeKafkaProducer.sendMessage(recipe);
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<Recipe>any());
    }

    /**
     * Method under test: {@link RecipeKafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage4() {
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<Recipe>any())).thenReturn(new CompletableFuture<>());
        Recipe recipe = mock(Recipe.class);
        doNothing().when(recipe).setDescription(Mockito.<String>any());
        doNothing().when(recipe).setName(Mockito.<String>any());
        doNothing().when(recipe).setRecipeIngredients(Mockito.<List<Recipe.RecipeIngredient>>any());
        doNothing().when(recipe).setSteps(Mockito.<List<String>>any());
        recipe.setDescription("The characteristics of someone or something");
        recipe.setName("Name");
        recipe.setRecipeIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        recipeKafkaProducer.sendMessage(recipe);
        verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<Recipe>any());
        verify(recipe).setDescription(Mockito.<String>any());
        verify(recipe).setName(Mockito.<String>any());
        verify(recipe).setRecipeIngredients(Mockito.<List<Recipe.RecipeIngredient>>any());
        verify(recipe).setSteps(Mockito.<List<String>>any());
    }
}

