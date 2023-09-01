package org.koffa.javafxgui.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.System.getProperty;

public class KafkaClient {
    private static final String BOOTSTRAP_SERVERS = getProperty("server", "localhost:9092");
    private static final String TOPIC = getProperty("topic", "recipeTopic");
    private static final String GROUP_ID = getProperty("user", "guiGroup");
    private static KafkaClient INSTANCE = null;
    private final KafkaConsumer<String, String> consumer;

    private KafkaClient() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(TOPIC));
    }
    public ConsumerRecords<String, String> pollRecords() {
        return consumer.poll(Duration.ofMillis(100));
    }
    public void commitAsync() {
        consumer.commitAsync();
    }
    public static KafkaClient getInstance() {
        if (INSTANCE == null)
            INSTANCE = new KafkaClient();
        return INSTANCE;
    }


}
