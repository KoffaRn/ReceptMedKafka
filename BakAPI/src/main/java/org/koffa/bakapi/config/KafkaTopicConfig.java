package org.koffa.bakapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic recipeTopic() {
        return TopicBuilder.name("recipeTopic")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
