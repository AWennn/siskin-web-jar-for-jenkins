package com.siskin.config;

import java.io.IOException;
import java.util.Properties;

public class SiskinWebConfig {

    public static String JAR_PATH = null;
    public static String FLINK_REST_URL = null;
    public static String JOB_NAME = null;
    public static Integer PARALLELISM = 0;
    public static String JAR_NAME = null;
    public static String ETL_NAME = null;

    static {

        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(System.getenv("SISKIN_HOME/conf")));
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("siskin-web.properties"));

            JAR_PATH = properties.getProperty("JAR_PATH");
            FLINK_REST_URL = properties.getProperty("FLINK_REST_URL");
            JOB_NAME = properties.getProperty("JOB_NAME");
            PARALLELISM = Integer.valueOf(properties.getProperty("PARALLELISM"));
            JAR_NAME = properties.getProperty("JAR_NAME");
            ETL_NAME = properties.getProperty("ETL_NAME");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
