package com.endava.petclinic.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvReader {

    private static final Logger LOGGER = LogManager.getLogger(EnvReader.class);
    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("env");
        InputStream resourceAsStream = EnvReader.class.getClassLoader().getResourceAsStream("env/" + env + ".properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't read property file", e);
        }
    }

    public static String getBaseUri() {
        return properties.getProperty("baseUri");
    }

    public static Integer getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public static String getBasePath() {
        return properties.getProperty("basePath");
    }

    public static String getAdminUsername() {
        return properties.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return properties.getProperty("admin.password");
    }

    public static String getDBUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDBPassword() {
        return properties.getProperty("db.password");
    }

    public static String getDBUsername() {
        return properties.getProperty("db.username");
    }
}
