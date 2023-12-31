package com.auth.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "jwt.config")
public class JWTObject {

    public static final String JWT_ISSUER = System.getenv("JWT_ISSUER");

    public static final String JWT_SECRET_KEY = System.getenv("JWT_SECRET_KEY");

}
