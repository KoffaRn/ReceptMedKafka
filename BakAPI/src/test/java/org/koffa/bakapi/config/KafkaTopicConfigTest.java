package org.koffa.bakapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;

class KafkaTopicConfigTest {
    /**
     * Method under test: {@link KafkaTopicConfig#recipeTopic()}
     */
    @Test
    void testRecipeTopic() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        NewTopic actualRecipeTopicResult = (new KafkaTopicConfig()).recipeTopic();
        assertNull(actualRecipeTopicResult.configs());
        assertNull(actualRecipeTopicResult.replicasAssignments());
        assertEquals("recipeTopic", actualRecipeTopicResult.name());
    }
}

