package org.koffa.javafxgui.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.koffa.javafxgui.recipegui.LoggerBox;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.System.getProperty;

public class KafkaClient implements Runnable {
    private volatile boolean running = true;
    private final List<String> topics;
    private final KafkaConsumer<String, String> consumer;
    private static final String BOOTSTRAP_SERVERS = getProperty("server", "localhost:9092");
    private static final String GROUP_ID = getProperty("user", "guiGroup");
    LoggerBox loggerBox;

    /***
     * Creates a new KafkaClient
     * @param loggerBox the loggerBox to log messages to
     */
    public KafkaClient(LoggerBox loggerBox) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.topics = new ArrayList<>();
        this.consumer = new KafkaConsumer<>(props);
        this.loggerBox = new LoggerBox();
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
            while (running) {
                synchronized(this) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records){
                        loggerBox.info("Message received from kafka >> " + record.value() + "\n");
                    }
                }
                try { Thread.sleep(100); } catch (Exception ignored) { }
            }
        } catch (Exception e){
            loggerBox.error("KafkaClient error consuming message >> ", e);
        }finally {
            consumer.close();
        }
    }

    public void interrupt() {
        running = false;
    }
}
