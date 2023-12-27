package com.auth.configs.ses;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "aws")
public class SesConfig {

    @Value("${ACCESS_KEY_ID}")
    public static final String ACCESS_KEY_ID = System.getenv("ACCESS_KEY_ID");

    @Value("${SECRET_KEY}")
    public static final String SECRET_KEY = System.getenv("SECRET_KEY");

    @Value("${REGION}")
    public static final String REGION = System.getenv("REGION");


}
