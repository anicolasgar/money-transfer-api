package com.revolut.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Config {
    private static Configuration config;

    static {
        try {
            CompositeConfiguration configuration = new CompositeConfiguration();

            PropertiesConfiguration props = new PropertiesConfiguration();
            props.read(
                    new BufferedReader(
                            new InputStreamReader(
                                    Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")
                            )
                    )
            );

            configuration.addConfiguration(props);
            config = configuration;

        } catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T getConfig(String key, Function<String, T> f) {

        return f.apply(key);
    }

    public static int getInt(String key) {
        return getConfig(key, config::getInt);
    }

    public static String getString(String key) {
        return getConfig(key, config::getString);
    }

    public static List<Object> getList(String key) {
        return getConfig(key, config::getList);
    }

    public static long getLong(String key) {
        return getConfig(key, config::getLong);
    }

    public static boolean getBoolean(String key) {
        return getConfig(key, config::getBoolean);
    }

    public static Boolean hasKey(String key){
        return config.containsKey(key);
    }

}
