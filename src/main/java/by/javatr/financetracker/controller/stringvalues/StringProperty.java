package by.javatr.financetracker.controller.stringvalues;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StringProperty {
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/java/by/javatr/financetracker/controller/stringvalues/controllerString.properties"));
        } catch (IOException ignored) {
        }
    }

    public static String getStringValue(String key){
        return properties.getProperty(key);
    }
}
