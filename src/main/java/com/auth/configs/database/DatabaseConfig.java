package com.auth.configs.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "prod")
@ConfigurationProperties(value = "spring.datasource")
public class DatabaseConfig {

    @Value("${RDS_DB_URL}")
    private static final String URL = System.getenv("RDS_DB_URL");

    @Value("${RDS_DB_USERNAME}")
    private static final String USERNAME = System.getenv("RDS_DB_USERNAME");

    @Value("${RDS_DB_PASSWORD}")
    private static final String PASSWORD = System.getenv("RDS_DB_PASSWORD");

}
