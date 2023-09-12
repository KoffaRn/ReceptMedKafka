package org.koffa.javafxgui.helpers;

import lombok.Getter;

import java.io.*;
import java.util.Properties;

import static java.lang.System.getProperty;

@Getter
public class ConfigManager {
    private static ConfigManager INSTANCE = new ConfigManager();
    private final String FILE_PATH = getProperty("user.dir") + "/gui.properties";
    private final String APP_NAME = "Recept med kafka";
    private final String KAFKA_SERVER;
    private final String KAFKA_TOPIC;
    private final String KAFKA_GROUP_ID;
    private final String API_URL;
    private final int APP_WIDTH = 800;
    private ConfigManager() {
        if (!configExists()) {
            generateConfig();
        }
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            Properties prop = new Properties();
            prop.load(input);
            KAFKA_SERVER = prop.getProperty("kafka.bootstrap.servers");
            KAFKA_TOPIC = prop.getProperty("kafka.topic");
            KAFKA_GROUP_ID = prop.getProperty("kafka.group-id");
            API_URL = prop.getProperty("api.url");
        } catch (IOException e) {
            throw new RuntimeException("Kunde inte läsa in config.properties", e);
        }
    }
    public static synchronized ConfigManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConfigManager();
        }
        return INSTANCE;
    }
    private void generateConfig() {
        try(OutputStream output = new FileOutputStream(FILE_PATH)) {
            File file = new File(FILE_PATH);
            if(file.createNewFile()) {
                System.out.println(FILE_PATH + " skapad");
                Properties prop = new Properties();
                prop.setProperty("kafka.bootstrap.servers", "localhost:9092");
                prop.setProperty("kafka.topic", "recipeTopic");
                prop.setProperty("kafka.group-id", "guiRecipe");
                prop.setProperty("api.url", "http://localhost:8080/api/v1/recipe");
                // save properties to project root folder, config.properties
                // props.store(output, null) will escape : and = characters, so use this instead
                for (String key : prop.stringPropertyNames()) {
                    output.write((key + "=" + prop.getProperty(key) + "\n").getBytes());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Kunde inte skapa config.properties", e);
        }
    }

    private boolean configExists() {
        File file = new File(FILE_PATH);
        return file.exists();
    }
}
