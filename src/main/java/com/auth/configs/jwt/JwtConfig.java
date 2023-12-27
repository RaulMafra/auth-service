package com.auth.configs.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "jwt.config")
public class JwtConfig {

    @Value("${ISSUER}")
    private static final String ISSUER = System.getenv("JWT_ISSUER");

    @Value("${SECRET_KEY}")
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

}
