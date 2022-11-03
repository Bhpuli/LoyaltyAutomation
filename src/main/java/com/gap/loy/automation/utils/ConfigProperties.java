package com.gap.loy.automation.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.Random;

public class ConfigProperties {

    private static Properties properties;
    private final String propertyFilePath= "src/test/resources/application.properties";

    public ConfigProperties() {
        readProperties();
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void readProperties() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        };

    }
    public String getUrl() {
        String url = properties.getProperty("stageURL");
        if (url == null) {
            System.out.println("stageURL property is empty, returning default value");
            return "https://www.stage.gaptechol.com/";
       }
       String[] split = url.split(",");
       return split[new Random().nextInt(split.length)];
    }

}
