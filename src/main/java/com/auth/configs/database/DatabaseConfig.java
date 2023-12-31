package com.auth.configs.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "prod")
@ConfigurationProperties(value = "spring.datasource")
public class DatabaseConfig {

    private static final String RDS_DB_URL = System.getenv("RDS_DB_URL");

    private static final String RDS_DB_USERNAME = System.getenv("RDS_DB_USERNAME");

    private static final String RDS_DB_PASSWORD = System.getenv("RDS_DB_PASSWORD");

}
