package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    static {
        try {
            String projectPath = System.getProperty("user.dir");
            //System.out.println("PROJECT PATH = " + projectPath);

            FileInputStream file =
                    new FileInputStream(projectPath + "/config.properties");

            properties.load(file);

            //System.out.println("URL FROM CONFIG = " + properties.getProperty("url"));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}