package org.koffa.javafxgui.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.koffa.javafxgui.recipegui.sendgui.LoggerBox;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaClient implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final List<String> topics;
    private final KafkaConsumer<String, String> consumer;
    private final LoggerBox loggerBox;
    /***
     * Creates a new KafkaClient
     * @param loggerBox the loggerBox to log messages to
     */

    public KafkaClient(String kafkaServer, String kafkaTopic, String kafkaGroupId, LoggerBox loggerBox) {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServer);
        props.put("group.id", kafkaGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.topics = new ArrayList<>();
        this.consumer = new KafkaConsumer<>(props);
        this.loggerBox = loggerBox;
        addTopic(kafkaTopic);
    }

    /**
     * Adds a topic to the list of topics to listen to
     * @param topic the topic to add
     */
    public void addTopic(String topic) {
        topics.add(topic);
        consumer.subscribe(topics);
    }
    @Override
    public void run() {
        try{
            while (running.get()) {
                synchronized(this) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records){
                        loggerBox.info("KafkaClient: Meddelande mottaget från kafka >> " + record.value());
                    }
                }
            }
        } catch (Exception e){
            loggerBox.error("KafkaClient: fel i konsumering av meddelande >> ", e);
        }finally {
            consumer.close();
        }
    }
    public void printAllMessages() {
        synchronized (this) {
            consumer.seekToBeginning(consumer.assignment());
            consumer.seekToEnd(consumer.assignment());
            ConsumerRecords<String, String> records;
            do {
                records = consumer.poll(Duration.ofMillis(10));
                for (ConsumerRecord<String, String> record : records) {
                    loggerBox.info("KafkaClient: Meddelande mottaget från kafka >> " + record.value());
                }
            } while (!records.isEmpty());
        }
    }

    public void interrupt() {
        running.set(false);
    }
}
