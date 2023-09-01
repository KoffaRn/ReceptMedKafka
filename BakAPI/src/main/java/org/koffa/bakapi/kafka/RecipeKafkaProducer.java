package org.koffa.bakapi.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.koffa.bakapi.dto.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecipeKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeKafkaProducer.class);
    private final KafkaTemplate<NewTopic, Recipe> kafkaTemplate;
    public RecipeKafkaProducer(KafkaTemplate<NewTopic, Recipe> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(Recipe recipe) {
        LOGGER.info(String.format("Producing message: %s", recipe.toString()));
        kafkaTemplate.send("recipeTopic", recipe);
    }
}