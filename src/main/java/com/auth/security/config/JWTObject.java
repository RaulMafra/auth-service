package com.auth.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "jwt.config")
public class JWTObject {

    private String ISSUER;
    private String SECRET_KEY;

    public String getISSUER() {
        return ISSUER;
    }

    public void setISSUER(String ISSUER) {
        this.ISSUER = ISSUER;
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }

    public void setSecretKey(String secretKey) {
        this.SECRET_KEY = secretKey;
    }
}
