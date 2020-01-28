package by.javatr.financetracker.service.stringvalues;

import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.Properties;

public class StringProperty {
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/java/resources/properties/serviceString.properties"));
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public static String getStringValue(String key){
        return properties.getProperty(key);
    }
}
