package org.koffa.gui.helpers;


public class ConfigManager {
    private static final String APP_NAME = "Recept med Kafka";
    private static String KAFKA_SERVER = "localhost:9092";
    private static String KAFKA_TOPIC = "recipeTopic";
    private static String KAFKA_GROUP_ID = "guiGroup";
    private static String API_URL = "http://localhost:8080/api/recipes";

    public ConfigManager() {
    }
    public static String getAppName() {
        return APP_NAME;
    }

    public static String getKafkaServer() {
        return KAFKA_SERVER;
    }

    public static String getKafkaTopic() {
        return KAFKA_TOPIC;
    }

    public static String getKafkaGroupId() {
        return KAFKA_GROUP_ID;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void setKafkaServer(String kafkaServer) {
        KAFKA_SERVER = kafkaServer;
    }

    public static void setKafkaTopic(String kafkaTopic) {
        KAFKA_TOPIC = kafkaTopic;
    }

    public static void setKafkaGroupId(String kafkaGroupId) {
        KAFKA_GROUP_ID = kafkaGroupId;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }
}
