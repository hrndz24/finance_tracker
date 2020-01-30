package by.javatr.financetracker.dao.stringvalues;

import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.Properties;

public class StringProperty {
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/properties/DAOString.properties"));
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public static String getStringValue(String key){
        return properties.getProperty(key);
    }
}
