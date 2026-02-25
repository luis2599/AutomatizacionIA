package pages;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class config {
    private static final Logger log = LoggerFactory.getLogger(config.class);
    private static Properties props = new Properties();

    static{
        try (InputStream input = config.class
                .getClassLoader()
                .getResourceAsStream("config.ambiente.properties")){
                    props.load(input);
                } catch (Exception e) {
                    log.error("Error cargando configuración: ", e.getMessage());
                }
    }

    public static String get(String key){
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
    
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }
}